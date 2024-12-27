package com.app.product.product_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductRequestDTO {

    @NotBlank(message = "Product name is required")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    // Accept either categoryId (existing) or categoryName (new)
    private Long categoryId;

    private String categoryName;  // Create new category if not found

    private String categoryDescription;  // Optional description for new categories

    @NotNull(message = "Stock is required")
    @Positive(message = "Stock must be positive")
    private Integer stock;
}
