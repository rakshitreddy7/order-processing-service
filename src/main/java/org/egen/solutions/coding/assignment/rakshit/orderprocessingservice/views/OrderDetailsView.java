package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.views;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.constants.OrderStatus;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.models.Item;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDetailsView {
    private UUID orderId;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;
    private List<Item> items;
    private Double orderTotal;
    private Double shippingCharges;
    private String shippingAddress;
    private String shippingZip;
}
