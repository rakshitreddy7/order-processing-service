package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public enum PaymentMethod {
    CARD,
    CASH,
    POINTS
}
