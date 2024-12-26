package com.app.order.order_service.service.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.order.order_service.model.Orders;
import com.app.order.order_service.repository.OrderRepository;
import com.app.order.order_service.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Long createOrder(Orders order) {
        validateOrder(order);
        return orderRepository.save(order).getOrderId();
    }

    @Override
    public Orders getOrder(Long orderId) {
        return orderRepository.findById(
                Optional.ofNullable(orderId)
                        .orElseThrow(() -> new IllegalArgumentException("Order Id cannot be null"))
        ).orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    /**
     * Validates the mandatory fields for order creation.
     *
     * @param order the order to validate
     */
    private void validateOrder(Orders order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        
        checkField(order.getTotalPrice(), "Total Price cannot be null");
        checkField(order.getProductId(), "Product Id cannot be null");
        checkField(order.getUserId(), "User Id cannot be null");
        checkField(order.getOrderStatus(), "Order Status cannot be null");
        checkField(order.getQuantity(), "Quantity cannot be null");
        
        checkString(order.getOrderName(), "Order Name cannot be blank");
        checkString(order.getShippingAddress(), "Shipping Address cannot be blank");
        checkString(order.getBillingAddress(), "Billing Address cannot be blank");
        checkString(order.getOrderCreatedBy(), "Order Created By cannot be blank");
    }

    /**
     * Validates that a field is not null.
     *
     * @param field the field to check
     * @param message the exception message if the field is null
     */
    private void checkField(Object field, String message) {
        if (field == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates that a string is not null or blank.
     *
     * @param field the string to check
     * @param message the exception message if the string is null or blank
     */
    private void checkString(String field, String message) {
        if (field == null || field.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }
}