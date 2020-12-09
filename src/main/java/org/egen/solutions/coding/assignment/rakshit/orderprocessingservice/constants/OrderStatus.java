package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.constants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public enum OrderStatus {
    ACTIVE,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED
}
