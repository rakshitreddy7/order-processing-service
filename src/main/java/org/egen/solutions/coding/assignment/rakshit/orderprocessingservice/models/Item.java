package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @NotNull (message = "itemId cannot be null")
    private Long itemId;
    @NotBlank (message = "orderItemName cannot be null")
    private String orderItemName;
    @NotNull (message = "orderItemQty cannot be null")
    private Integer orderItemQty;
}
