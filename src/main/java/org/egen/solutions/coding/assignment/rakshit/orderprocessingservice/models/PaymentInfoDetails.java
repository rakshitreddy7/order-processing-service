package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentInfoDetails {
    @NotNull (message = "paymentMethod cannot be null")
    private PaymentMethod paymentMethod;

    @NotNull (message = "amount cannot be null")
    private Double amount;
}
