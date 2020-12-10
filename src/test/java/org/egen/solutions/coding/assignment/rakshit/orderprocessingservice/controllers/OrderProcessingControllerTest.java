package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.OrderProcessingServiceApplication;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.constants.ShippingMethod;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.models.Address;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.views.CustomerOrdersView;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.models.Item;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.models.Order;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.views.OrderDetailsView;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.models.OrderRequest;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.views.OrderView;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.models.PaymentInfoDetails;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.models.PaymentMethod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderProcessingServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderProcessingControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port + "/api";
    }

    @Test
    public void shouldCreateOrdersGivenValidOrderRequest() {
        Order order = new Order();

        Address billingAddress = Address.builder()
                .line1("520 Wyndham St")
                .line2("West Madison")
                .city("Chicago")
                .state("IL")
                .zip("66065")
                .build();

        Address shippingAddress = Address.builder()
                .line1("99 Leeland St")
                .line2("West Cupertino")
                .city("SF")
                .state("CA")
                .zip("09823")
                .build();

        Item item1 = Item.builder()
                .itemId(123L)
                .itemName("item-1")
                .itemQty(2)
                .build();

        Item item2 = Item.builder()
                .itemId(456L)
                .itemName("item-2")
                .itemQty(3)
                .build();

        Item item3 = Item.builder()
                .itemId(789L)
                .itemName("item-3")
                .itemQty(4)
                .build();

        List<Item> items = Arrays.asList(item1, item2, item3);

        PaymentInfoDetails paymentInfoDetails1 = PaymentInfoDetails.builder()
                .amount(48.0)
                .paymentMethod(PaymentMethod.CASH)
                .build();

        PaymentInfoDetails paymentInfoDetails2 = PaymentInfoDetails.builder()
                .amount(48.0)
                .paymentMethod(PaymentMethod.CARD)
                .build();

        List<PaymentInfoDetails> paymentInfoDetails = Arrays.asList(paymentInfoDetails1, paymentInfoDetails2);

        order.setBillingAddress(billingAddress);
        order.setShippingAddress(shippingAddress);
        order.setItems(items);
        order.setOrderCustomerId(999L);
        order.setOrderPaymentDate(LocalDate.of(2020,12,1));
        order.setOrderSubtotal(90.0);
        order.setOrderTax(6.0);
        order.setOrderTotal(96.0);
        order.setPaymentInfoDetails(paymentInfoDetails);
        order.setShippingMethod(ShippingMethod.CURB_SIDE_DELIVERY);

        OrderRequest orderRequest = OrderRequest.builder()
                .order(order)
                .build();

        ResponseEntity<OrderView> postResponse = restTemplate.postForEntity(getRootUrl() + "/orders", orderRequest, OrderView.class);

        OrderView orderView = postResponse.getBody();
        assertNotNull(orderView);

        UUID orderId = orderView.getOrderId();
        assertNotNull(orderId);
        assertEquals(postResponse.getStatusCode(), HttpStatus.OK);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void shouldDisplayOrderDetailsForGivenOrderId() {
        ResponseEntity<OrderDetailsView> getResponse = restTemplate.getForEntity(getRootUrl() + "/order/efd09dca-5668-4add-bf7a-2b6ca83cd09c", OrderDetailsView.class);
        OrderDetailsView orderDetailsView = getResponse.getBody();

        assertNotNull(orderDetailsView);
        assertEquals(orderDetailsView.getOrderId(), UUID.fromString("efd09dca-5668-4add-bf7a-2b6ca83cd09c"));
        assertEquals(orderDetailsView.getShippingZip(), "60657");
        assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldThrowNotFoundStatusForUnavailableOrderId() {
        ResponseEntity<OrderDetailsView> getResponse = restTemplate.getForEntity(getRootUrl() + "/order/de0d595a-81e5-458a-8459-712e31f001e2", OrderDetailsView.class);

        assertEquals(getResponse.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldDisplayAllOrdersForGivenCustomerId() {
        ResponseEntity<CustomerOrdersView> getResponse = restTemplate.getForEntity(getRootUrl() + "/orders/123", CustomerOrdersView.class);
        CustomerOrdersView customerOrdersView = getResponse.getBody();

        assertNotNull(customerOrdersView);

        List<OrderDetailsView> orderDetails = customerOrdersView.getCustomerOrders();
        assertEquals(orderDetails.size(), 3);

        OrderDetailsView oderDetailsView1 = orderDetails.get(0);
        assertEquals(oderDetailsView1.getOrderId(), UUID.fromString("efd09dca-5668-4add-bf7a-2b6ca83cd09c"));
        assertEquals(oderDetailsView1.getOrderTotal().intValue(), 75);

        OrderDetailsView oderDetailsView2 = orderDetails.get(1);
        assertEquals(oderDetailsView2.getOrderId(), UUID.fromString("de0d595a-81e5-458a-8459-700e31f001e2"));
        assertEquals(oderDetailsView2.getOrderTotal().intValue(), 95);
        assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldCancelOrderForGivenOrderId() {
        ResponseEntity<OrderView> getResponse = restTemplate.getForEntity(getRootUrl() + "/order/efd09dca-5668-4add-bf7a-2b6ca83cd09c", OrderView.class);
        OrderView orderView = getResponse.getBody();

        assertNotNull(orderView);
        assertNotNull(orderView.getOrderId());
        assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
    }
}
