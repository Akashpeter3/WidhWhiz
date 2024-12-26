package com.app.order.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.order.order_service.model.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

}
