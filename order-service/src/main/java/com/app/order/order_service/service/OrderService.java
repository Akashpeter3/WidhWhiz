package com.app.order.order_service.service;

import org.springframework.stereotype.Service;

import com.app.order.order_service.model.Orders;


public interface OrderService {
    Long createOrder(Orders order);
    Orders getOrder(Long orderId);

}
