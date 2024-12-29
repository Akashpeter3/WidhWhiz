package com.app.product.product_service.service.serviceImpl;

import org.springframework.stereotype.Service;

import com.app.product.product_service.dto.InventoryDTO;
import com.app.product.product_service.model.Inventory;
import com.app.product.product_service.repository.InventoryRepository;
import com.app.product.product_service.service.InventoryService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;


    @Override
    public void updateStock(InventoryDTO inventoryDTO,int quantity) {
       
        Inventory inventory = inventoryRepository.findByProduct_ProductId(inventoryDTO.getProductId());
        if (inventory == null) {
            throw new EntityNotFoundException("Inventory not found for product id: " + inventoryDTO.getProductId());
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity cannot be zero or negative");
        }else if(quantity>0&&quantity<=inventory.getStock()){
            inventory.setStock(inventory.getStock() - quantity);
            inventoryRepository.save(inventory);
        }

       // inventory.setStock(inventoryDTO.getStock());
        
       
    }

    @Override
    public Boolean checkStock(Long productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProduct_ProductId(productId);
        if (inventory == null) {
            throw new EntityNotFoundException("Inventory not found for product id: " + productId);
        }
        return inventory.getStock() >= quantity;

       
    }




}
