package com.app.product.product_service.service;

import com.app.product.product_service.dto.InventoryDTO;

public interface InventoryService {
    void updateStock(InventoryDTO inventoryDTO);
    Boolean checkStock(Long productId,int quantity);

}
