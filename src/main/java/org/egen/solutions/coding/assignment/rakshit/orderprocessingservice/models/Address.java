package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.models;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class Address {
    @NotBlank
    private String line1;

    private String line2;

    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String zip;
}
