package com.app.product.product_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseDTO {
    private Long productId;
    private String name;
    private String description;
    private Double price;
    private String categoryName;
    private Integer stock; 
}
