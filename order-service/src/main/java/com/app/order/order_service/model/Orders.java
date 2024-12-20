package com.app.order.order_service.model;

import java.time.LocalDateTime;

import com.app.order.order_service.util.OrderStatus;
import com.app.order.order_service.util.OrderType;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_seq", initialValue = 10000, allocationSize = 1)
    private Long orderId;

    @NotBlank
    @Column(nullable = false)
    private String orderName;

    private String orderDescription;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    private String orderCategory;

    @NotBlank
    @Column(nullable = false)
    private String orderCreatedBy;

    private LocalDateTime orderUpdatedDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime orderCreatedDate;

    @NotNull
    @Column(nullable = false)
    private Long userId;

    @NotNull
    @Column(nullable = false)
    private Long productId;

    @NotNull
    @Column(nullable = false)
    private Integer quantity;

    @NotNull
    @Column(nullable = false)
    private Double totalPrice;

    @NotBlank
    @Column(nullable = false)
    private String shippingAddress;

    @NotBlank
    @Column(nullable = false)
    private String billingAddress;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime orderPlacedDate;

    @PrePersist
    protected void onCreate() {
        orderCreatedDate = LocalDateTime.now();
        orderPlacedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        orderUpdatedDate = LocalDateTime.now();
    }
}
