package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.constants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public enum ShippingMethod {
    IN_STORE_PICKUP,
    CURB_SIDE_DELIVERY,
    SHIP_TO_HOME
}
