package com.app.product.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.product.product_service.model.Inventory;
import com.app.product.product_service.model.Product;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Inventory findByProduct_ProductId(Long productId);

    // Add method to delete inventory by product
    void deleteByProduct(Product product);

    // Optional: If you need to delete by productId directly
    void deleteByProduct_ProductId(Long productId);
}