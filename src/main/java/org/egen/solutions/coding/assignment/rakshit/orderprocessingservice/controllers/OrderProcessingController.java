package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.controllers;

import io.swagger.annotations.ApiOperation;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.exceptions.OrderDataNotFoundException;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.views.CustomerOrdersView;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.views.OrderView;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.views.OrderDetailsView;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.models.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.services.OrderProcessingService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Validated
public class OrderProcessingController {

    private static final String CONTENT_TYPE = "application/json";

    private OrderProcessingService orderProcessingService;

    @Autowired
    public OrderProcessingController(final OrderProcessingService orderProcessingService) {
        this.orderProcessingService = orderProcessingService;
    }

    @ApiOperation(value = "Create an order in the postgresDB", notes = "Provide a request body to save an order", response = OrderView.class)
    @PostMapping(value = "/orders", consumes = CONTENT_TYPE, produces = CONTENT_TYPE)
    @ResponseBody
    public ResponseEntity<OrderView> createOrder(@RequestBody @Valid OrderRequest request) {
        UUID orderId = orderProcessingService.createOrder(request);
        OrderView orderView = OrderView.builder()
                .orderId(orderId)
                .build();

        return ResponseEntity.ok().body(orderView);
    }

    @ApiOperation(value = "Find an order by orderId", notes = "Provide an orderId to search for an order", response = OrderDetailsView.class)
    @GetMapping(value = "/order/{orderId}", produces = CONTENT_TYPE)
    public ResponseEntity<OrderDetailsView> getOrderByOrderId(@PathVariable(value = "orderId") final UUID orderId)
            throws OrderDataNotFoundException {
        OrderDetailsView orderDetailsView = orderProcessingService.getOrderByOrderId(orderId);

        return ResponseEntity.ok().body(orderDetailsView);
    }

    @ApiOperation(value = "Find all the orders by customerId", notes = "Provide all the orders placed by a customerId", response = CustomerOrdersView.class)
    @GetMapping(value = "/orders/{customerId}", produces = CONTENT_TYPE)
    public ResponseEntity<CustomerOrdersView> getOrdersByCustomerId(@PathVariable(value = "customerId") final Long customerId)
            throws OrderDataNotFoundException {
        CustomerOrdersView customerOrdersView = orderProcessingService.getOrdersByCustomerId(customerId);

        return ResponseEntity.ok().body(customerOrdersView);
    }

    @ApiOperation(value = "Cancel an order by orderId", notes = "Cancel an order placed by an orderId", response = OrderView.class)
    @PutMapping(value = "/order/{orderId}", produces = CONTENT_TYPE)
    @ResponseBody
    public ResponseEntity<OrderView> cancelOrderByOrderId(@PathVariable(value = "orderId") final UUID orderId)
            throws OrderDataNotFoundException {
        UUID id = orderProcessingService.cancelOrder(orderId);
        OrderView orderView = OrderView.builder()
                .orderId(id)
                .build();
        return ResponseEntity.ok().body(orderView);
    }
}


