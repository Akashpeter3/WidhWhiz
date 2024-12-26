package com.app.order.order_service.model;

import java.time.LocalDateTime;
import java.util.Optional;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.app.order.order_service.util.OrderStatus;
import com.app.order.order_service.util.OrderType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {

    @Id
    @GeneratedValue(generator = "order-id-seq")
    @GenericGenerator(
        name = "order-id-seq",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
            @Parameter(name = "sequence_name", value = "order_seq"),
            @Parameter(name = "initial_value", value = "1000"),
            @Parameter(name = "increment_size", value = "1")
        }
    )
    private Long orderId;

    @NotBlank(message = "Order name is required")
    @Column(nullable = false)
    private String orderName;

    @Column
    private String orderDescription;

    @NotNull(message = "Order status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column
    private OrderType orderType;

    @Column
    private String orderCategory;

    @NotBlank(message = "Order creator is required")
    @Column(nullable = false)
    private String orderCreatedBy;

    @Column
    private LocalDateTime orderUpdatedDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime orderCreatedDate;

    @NotNull(message = "User ID is required")
    @Column(nullable = false)
    private Long userId;

    @NotNull(message = "Product ID is required")
    @Column(nullable = false)
    private Long productId;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    @Column(nullable = false)
    private Integer quantity;

    @NotNull(message = "Total price is required")
    @Positive(message = "Total price must be positive")
    @Column(nullable = false)
    private Double totalPrice;

    @NotBlank(message = "Shipping address is required")
    @Column(nullable = false)
    private String shippingAddress;

    @NotBlank(message = "Billing address is required")
    @Column(nullable = false)
    private String billingAddress;

    @NotNull(message = "Order placed date is required")
    @Column(nullable = false)
    private LocalDateTime orderPlacedDate;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        orderCreatedDate = now;
        orderPlacedDate = Optional.ofNullable(orderPlacedDate).orElse(now);
    }

    @PreUpdate
    protected void onUpdate() {
        orderUpdatedDate = LocalDateTime.now();
    }
}