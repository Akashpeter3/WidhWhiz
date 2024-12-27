package com.app.product.product_service.service;

import java.util.List;

import com.app.product.product_service.dto.ProductRequestDTO;
import com.app.product.product_service.dto.ProductResponseDTO;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);
    ProductResponseDTO getProductById(Long productId);
    ProductResponseDTO updateProduct(Long productId, ProductRequestDTO productRequestDTO);
    public void deleteProduct(Long productId);
    List<ProductResponseDTO> getAllProducts();
}
