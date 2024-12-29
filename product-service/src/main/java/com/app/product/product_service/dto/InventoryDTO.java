package com.app.product.product_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryDTO {
    private Long productId;
    private Integer stock;
}
