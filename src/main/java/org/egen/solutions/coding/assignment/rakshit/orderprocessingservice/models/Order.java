package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.constants.ShippingMethod;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@NotNull
@Valid
public class Order {
    @NotNull (message = "CustomerId cannot be null")
    private Long orderCustomerId;

    @NotNull (message = "orderSubTotal cannot be null")
    private Double orderSubtotal;

    @NotNull (message = "orderTax cannot be null")
    private Double orderTax;

    @NotNull (message = "orderTotal cannot be null")
    private Double orderTotal;

    @Valid
    @NotNull (message = "paymentInfoDetails cannot be null")
    private List<PaymentInfoDetails> paymentInfoDetails;

    @Valid
    @NotNull (message = "items cannot be null")
    private List<Item> items;

    @NotNull (message = "orderPaymentDate cannot be null")
    private LocalDate orderPaymentDate;

    @Valid
    @NotNull (message = "billingAddress cannot be null")
    private Address billingAddress;

    @Valid
    @NotNull (message = "shippingAddress cannot be null")
    private Address shippingAddress;

    @NotNull (message = "shippingMethod cannot be null")
    private ShippingMethod shippingMethod;
}
