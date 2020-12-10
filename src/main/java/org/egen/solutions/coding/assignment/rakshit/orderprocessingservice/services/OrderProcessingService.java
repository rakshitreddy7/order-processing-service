package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.services;

import org.apache.commons.lang3.StringUtils;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.constants.OrderStatus;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.exceptions.OrderDataNotFoundException;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.clients.BillingDetailsClient;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.clients.CityStateDetailsClient;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.clients.ItemDetailsClient;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.clients.ItemQuantityClient;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.clients.OrderDetailsClient;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.clients.ZipCodeCityClient;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.mappers.OrderDetailsMapper;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.models.Address;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.views.CustomerOrdersView;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.models.Item;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.views.OrderDetailsView;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.models.OrderRequest;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.models.Order;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.models.PaymentInfoDetails;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.models.PaymentMethod;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.tables.BillingDetails;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.tables.CityState;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.tables.ItemDetails;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.tables.ItemQuantity;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.tables.OrderDetails;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.tables.OrderItemComposite;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.tables.PaymentDetails;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.tables.ZipCodeCity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderProcessingService {

    private static final String LINE_SEPARATOR = " ";
    private static final String ORDER_DETAILS_NOT_FOUND = "OrderDetails not found for the orderId: ";
    private static final String ITEM_DETAILS_NOT_FOUND = "ItemDetails not found for the itemId: ";
    private static final String ORDER_DETAILS_NOT_FOUND_FOR_CUSTOMER = "OrderDetails not found for the customerId: ";
    private static final String ARE_SUCCESSFULLY_SAVED = " are successfully saved.";
    private static final Logger logger = LoggerFactory.getLogger(OrderProcessingService.class);

    private ItemDetailsClient itemDetailsClient;
    private CityStateDetailsClient cityStateDetailsClient;
    private ZipCodeCityClient zipCodeCityClient;
    private BillingDetailsClient billingDetailsClient;
    private OrderDetailsClient orderDetailsClient;
    private ItemQuantityClient itemQuantityClient;
    private OrderDetailsMapper orderDetailsMapper;


    @Autowired
    public OrderProcessingService(final ItemDetailsClient itemDetailsClient,
                                  final CityStateDetailsClient cityStateDetailsClient,
                                  final ZipCodeCityClient zipCodeCityClient,
                                  final BillingDetailsClient billingDetailsClient,
                                  final OrderDetailsClient orderDetailsClient,
                                  final ItemQuantityClient itemQuantityClient,
                                  final OrderDetailsMapper orderDetailsMapper) {
        this.itemDetailsClient = itemDetailsClient;
        this.cityStateDetailsClient = cityStateDetailsClient;
        this.zipCodeCityClient = zipCodeCityClient;
        this.billingDetailsClient = billingDetailsClient;
        this.orderDetailsClient = orderDetailsClient;
        this.itemQuantityClient = itemQuantityClient;
        this.orderDetailsMapper = orderDetailsMapper;
    }

    @Cacheable(value = "orderByOrderIdCache", key = "#orderId")
    public OrderDetailsView getOrderByOrderId(UUID orderId) throws OrderDataNotFoundException {
       OrderDetails orderDetails = orderDetailsClient.findById(orderId)
                .orElseThrow(() -> new OrderDataNotFoundException(ORDER_DETAILS_NOT_FOUND + orderId));

        List<Item> items = populateItems(orderId);

        OrderDetailsView orderDetailsView = orderDetailsMapper.map(orderDetails, items);

        logger.info("Fetching OrderDetails.......");
        return orderDetailsView;
    }

    @Cacheable(value = "ordersByCustomerIdCache", key = "#customerId")
    public CustomerOrdersView getOrdersByCustomerId(Long customerId) throws OrderDataNotFoundException {
        List<OrderDetails> orderDetails = orderDetailsClient.findByCustomerIdOrderByCreatedDateDesc(customerId)
                .orElseThrow(() -> new OrderDataNotFoundException(ORDER_DETAILS_NOT_FOUND_FOR_CUSTOMER + customerId));

        List<OrderDetailsView> orderDetailsViews = new ArrayList<>();

        for (OrderDetails order : orderDetails) {
            UUID orderId = order.getOrderId();
            List<Item> items = populateItems(orderId);
            OrderDetailsView orderDetailsView = orderDetailsMapper.map(order, items);
            orderDetailsViews.add(orderDetailsView);
        }

        logger.info("Fetching all the OrderDetails by a customer.......");
        return CustomerOrdersView.builder()
                .customerOrders(orderDetailsViews)
                .build();
    }

    private List<Item> populateItems(UUID orderId) throws OrderDataNotFoundException {
        List<ItemQuantity> itemQuantities = itemQuantityClient.findByOrOrderItemCompositeKeyOrderId(orderId)
                .orElseThrow(() -> new OrderDataNotFoundException(ORDER_DETAILS_NOT_FOUND + orderId));

        List<Item> items = new ArrayList<>();

        for (ItemQuantity itemQuantity : itemQuantities) {
                Long itemId = itemQuantity.getOrderItemCompositeKey().getItemId();
                ItemDetails itemDetails = itemDetailsClient.findById(itemId)
                        .orElseThrow(() -> new OrderDataNotFoundException(ITEM_DETAILS_NOT_FOUND + itemId + " for OrderId: " + orderId));

                Item item = Item.builder()
                        .itemId(itemDetails.getId())
                        .orderItemName(itemDetails.getName())
                        .orderItemQty(itemQuantity.getQuantity())
                        .build();

                items.add(item);
        }
        return items;
    }

    public UUID createOrder(OrderRequest request) {
        UUID orderId = putOrderData(request);
        putItemsData(request);
        putCityStateData(request);
        putZipCodeCityData(request);
        putBillingDetailsData(request);

        return orderId;
    }

    private UUID putOrderData(OrderRequest request) {
        Order requestedOrder = request.getOrder();
        List<ItemQuantity> itemDetails = new ArrayList<>();
        List<Item> items = requestedOrder.getItems();
        items.forEach(item -> {
            Long itemId = item.getItemId();
            Integer orderItemQty = item.getOrderItemQty();

            OrderItemComposite orderItemComposite = OrderItemComposite.builder()
                    .itemId(itemId)
                    .build();

            ItemQuantity itemQuantity = ItemQuantity.builder()
                    .orderItemCompositeKey(orderItemComposite)
                    .quantity(orderItemQty)
                    .build();
            itemDetails.add(itemQuantity);
        });

        List<PaymentDetails> payments = new ArrayList<>();
        List<PaymentInfoDetails> paymentInfoDetails = requestedOrder.getPaymentInfoDetails();
        paymentInfoDetails.forEach(payment -> {
            PaymentMethod paymentMethod = payment.getPaymentMethod();
            Double amount = payment.getAmount();

            PaymentDetails paymentDetailsRow = PaymentDetails.builder()
                    .amount(amount)
                    .method(paymentMethod)
                    .date(requestedOrder.getOrderPaymentDate())
                    .build();
            payments.add(paymentDetailsRow);
        });

        Address shippingAddress = requestedOrder.getShippingAddress();
        String address = buildAddress(shippingAddress);

        OrderDetails orderDetails = OrderDetails.builder()
                .customerId(requestedOrder.getOrderCustomerId())
                .total(requestedOrder.getOrderTotal())
                .tax(requestedOrder.getOrderTax())
                .subTotal(requestedOrder.getOrderSubtotal())
                .status(OrderStatus.ACTIVE)
                .shippingAddress(address)
                .shippingZipCode(requestedOrder.getShippingAddress().getZip())
                .shippingMethod(requestedOrder.getShippingMethod())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        payments.forEach(orderDetails::addPayment);
        itemDetails.forEach(orderDetails::addItem);

        OrderDetails savedOrder = orderDetailsClient.save(orderDetails);
        UUID orderId = savedOrder.getOrderId();
        Long customerId = savedOrder.getCustomerId();

        logger.info("OrderDetails for the orderId: " + orderId + " and customerId: " + customerId + ARE_SUCCESSFULLY_SAVED);

        return orderId;
    }

    private void putItemsData(OrderRequest request) {
        Order requestedOrder = request.getOrder();
        List<Item> items = requestedOrder.getItems();
        items.forEach(item -> {
            Long itemId = item.getItemId();
            String itemName = item.getOrderItemName();

            ItemDetails itemDetailsData = ItemDetails.builder()
                    .id(itemId)
                    .name(itemName)
                    .build();

            itemDetailsClient.save(itemDetailsData);

            logger.info("ItemDetails for the itemId: " + itemId + ARE_SUCCESSFULLY_SAVED);
        });
    }

    private String buildAddress(Address address) {
        StringBuilder addressInfo = new StringBuilder();

        String line1 = address.getLine1();
        String line2 = address.getLine2();

        if (!line1.isEmpty()) {
            addressInfo.append(line1).append(LINE_SEPARATOR);
        }
        if (StringUtils.isNotBlank(line2)) {
            addressInfo.append(line2);
        }

        String addressLine = addressInfo.toString();

        return StringUtils.isBlank(addressLine) ? null : addressLine;
    }

    private void putCityStateData(OrderRequest request) {
        Order requestedOrder = request.getOrder();

        String shippingCity = requestedOrder.getShippingAddress().getCity();
        String shippingState = requestedOrder.getShippingAddress().getState();

        CityState shippingDetails = CityState.builder()
                .city(shippingCity)
                .state(shippingState)
                .build();

        cityStateDetailsClient.save(shippingDetails);
        logger.info("City and State Details for the city: " + shippingCity + ARE_SUCCESSFULLY_SAVED);

        String billingCity = requestedOrder.getBillingAddress().getCity();
        String billingState = requestedOrder.getBillingAddress().getState();

        CityState billingDetails = CityState.builder()
                .city(billingCity)
                .state(billingState)
                .build();

        cityStateDetailsClient.save(billingDetails);
        logger.info("City and State Details for the city: " + billingCity + ARE_SUCCESSFULLY_SAVED);
    }

    private void putZipCodeCityData(OrderRequest request) {
        Order requestedOrder = request.getOrder();

        String shippingCity = requestedOrder.getShippingAddress().getCity();
        String shippingZip = requestedOrder.getShippingAddress().getZip();

        ZipCodeCity zipCodeCityShippingDetails = ZipCodeCity.builder()
                .city(shippingCity)
                .zipCode(shippingZip)
                .build();

        zipCodeCityClient.save(zipCodeCityShippingDetails);
        logger.info("ZipCode and City Details for the city: " + shippingCity + ARE_SUCCESSFULLY_SAVED);

        String billingCity = requestedOrder.getBillingAddress().getCity();
        String billingZip = requestedOrder.getBillingAddress().getZip();

        ZipCodeCity zipCodeCityBillingDetails = ZipCodeCity.builder()
                .city(billingCity)
                .zipCode(billingZip)
                .build();

        zipCodeCityClient.save(zipCodeCityBillingDetails);
        logger.info("ZipCode and City Details for the city: " + billingCity + ARE_SUCCESSFULLY_SAVED);
    }

    private void putBillingDetailsData(OrderRequest request) {
        Order requestedOrder = request.getOrder();
        Long customerId = requestedOrder.getOrderCustomerId();
        Address billingAddress = requestedOrder.getBillingAddress();
        String address = buildAddress(billingAddress);
        String zip = requestedOrder.getBillingAddress().getZip();

        BillingDetails billingDetails = BillingDetails.builder()
                .customerId(customerId)
                .address(address)
                .zip(zip)
                .build();

        billingDetailsClient.save(billingDetails);
        logger.info("Billing Details for the customerId: " + customerId + ARE_SUCCESSFULLY_SAVED);
    }

    public UUID cancelOrder(UUID orderId) throws OrderDataNotFoundException {
        OrderDetails orderDetails = orderDetailsClient.findById(orderId)
                .orElseThrow(() -> new OrderDataNotFoundException(ORDER_DETAILS_NOT_FOUND + orderId));

        orderDetails.setStatus(OrderStatus.CANCELLED);
        orderDetails.setLastModifiedDate(LocalDateTime.now());
        OrderDetails savedOrder = orderDetailsClient.save(orderDetails);
        logger.info("OrderId: " + orderId + " is successfully cancelled.");

        return savedOrder.getOrderId();
    }
}
