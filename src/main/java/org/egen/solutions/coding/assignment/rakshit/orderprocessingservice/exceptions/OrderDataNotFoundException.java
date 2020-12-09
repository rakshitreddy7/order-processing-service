package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class OrderDataNotFoundException extends Exception {

    public OrderDataNotFoundException(String message) {
        super(message);
    }
}
