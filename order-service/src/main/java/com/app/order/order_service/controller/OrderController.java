package com.app.order.order_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Long orderId = orderService.createOrder(order);
        return ResponseEntity.ok("Order created successfully with ID: " + orderId);
    }

    /**
     * Endpoint to fetch an order by ID.
     * @param orderId the ID of the order to retrieve
     * @return ResponseEntity with the requested order
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> getOrder(@PathVariable Long orderId) {
        Orders order = orderService.getOrder(orderId);
        return ResponseEntity.ok(order);
    }
}