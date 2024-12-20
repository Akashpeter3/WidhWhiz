package com.app.order.order_service.util;

public enum OrderStatus {

    CREATED, SHIPPED, DELIVERED, CANCELLED;

    // public static OrderStatus getOrderStatus(String status) {
    //     for (OrderStatus orderStatus : OrderStatus.values()) {
    //         if (orderStatus.name().equalsIgnoreCase(status)) {
    //             return orderStatus;
    //         }
    //     }
    //     return null;
    // }

}
