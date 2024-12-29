package com.app.order.order_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.app.order.order_service.model.Orders;
import com.app.order.order_service.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Endpoint to create a new order.
     * @param order the order request body (validated)
     * @return ResponseEntity with order ID
     */
    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@Valid @RequestBody Orders order) {
        try {
            Long orderId = orderService.createOrder(order);
            return ResponseEntity.ok("Order created successfully with ID: " + orderId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to create order", e);
        }
    }

    /**
     * Endpoint to fetch an order by ID.
     * @param orderId the ID of the order to retrieve
     * @return ResponseEntity with the requested order
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> getOrder(@PathVariable Long orderId) {
        try {
            Orders order = orderService.getOrder(orderId);
            if (order == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
            }
            return ResponseEntity.ok(order);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to fetch order", e);
        }
    }

    /**
     * Global exception handler for ResponseStatusException.
     * @param ex the exception
     * @return ResponseEntity with error message
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
    }
}