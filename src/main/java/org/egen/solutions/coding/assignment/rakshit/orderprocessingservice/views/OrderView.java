package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderView {
    private UUID orderId;
}
