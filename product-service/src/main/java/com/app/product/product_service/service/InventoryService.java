package com.app.product.product_service.service;

import com.app.product.product_service.dto.InventoryDTO;

public interface InventoryService {
    void updateStock(InventoryDTO inventoryDTO);
    Integer checkStock(Long productId);

}
