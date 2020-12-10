package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderView {
    private UUID orderId;
}
