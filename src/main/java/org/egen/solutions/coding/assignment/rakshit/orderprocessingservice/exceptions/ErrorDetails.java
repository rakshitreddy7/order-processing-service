package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDetails {
    private String details;
    private String message;

    public ErrorDetails() {
        super();

        this.details = details;
        this.message = message;
    }
}
