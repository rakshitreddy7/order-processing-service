package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.mappers;

import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.constants.OrderStatus;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.constants.ShippingMethod;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.models.Item;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.views.OrderDetailsView;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.tables.OrderDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class OrderDetailsMapper {

    private static Map<ShippingMethod,Double> shippingCharges = new HashMap<>();
    
    static {
        shippingCharges.put(ShippingMethod.IN_STORE_PICKUP, 0.0);
        shippingCharges.put(ShippingMethod.CURB_SIDE_DELIVERY, 0.0);
        shippingCharges.put(ShippingMethod.SHIP_TO_HOME, 10.0);
    }

    public OrderDetailsView map(OrderDetails orderDetails, List<Item> items) {
        UUID orderId = orderDetails.getOrderId();
        OrderStatus orderStatus = orderDetails.getStatus();
        LocalDateTime orderDate = orderDetails.getCreatedDate();
        Double orderTotal = orderDetails.getTotal();
        String shippingAddress = orderDetails.getShippingAddress();
        String shippingZip = orderDetails.getShippingZipCode();
        ShippingMethod shippingMethod = orderDetails.getShippingMethod();

        return OrderDetailsView.builder()
                .orderId(orderId)
                .orderStatus(orderStatus)
                .orderDate(orderDate)
                .items(items)
                .orderTotal(orderTotal)
                .shippingAddress(shippingAddress)
                .shippingZip(shippingZip)
                .shippingCharges(getShippingCharges(shippingMethod))
                .build();
    }

    private Double getShippingCharges(ShippingMethod shippingMethod) {
        return shippingCharges.getOrDefault(shippingMethod, 0.0);
    }
}
