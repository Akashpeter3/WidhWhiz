package com.app.product.product_service.service.serviceImpl;

import org.springframework.stereotype.Service;

import com.app.product.product_service.dto.InventoryDTO;
import com.app.product.product_service.model.Inventory;
import com.app.product.product_service.repository.InventoryRepository;
import com.app.product.product_service.service.InventoryService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;


    @Override
    public void updateStock(InventoryDTO inventoryDTO) {
       
        Inventory inventory = inventoryRepository.findByProduct_ProductId(inventoryDTO.getProductId());
        if (inventory == null) {
            throw new EntityNotFoundException("Inventory not found for product id: " + inventoryDTO.getProductId());
        }
        inventory.setStock(inventoryDTO.getStock());
        inventoryRepository.save(inventory);
       
    }

    @Override
    public Integer checkStock(Long productId) {
        Inventory inventory = inventoryRepository.findByProduct_ProductId(productId);
       return  (inventory!=null)?inventory.getStock():0;

       
    }




}
