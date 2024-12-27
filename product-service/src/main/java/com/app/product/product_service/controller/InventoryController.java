package com.app.product.product_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.product.product_service.dto.InventoryDTO;
import com.app.product.product_service.service.InventoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
      private final InventoryService inventoryService;

    // Update stock for a product
    @PutMapping("/update")
    public ResponseEntity<String> updateStock(@Valid @RequestBody InventoryDTO inventoryDTO) {
        inventoryService.updateStock(inventoryDTO);
        return ResponseEntity.ok("Stock updated successfully for Product ID: " + inventoryDTO.getProductId());
    }

    // Check stock for a product
    @GetMapping("/check/{productId}")
    public ResponseEntity<Integer> checkStock(@PathVariable Long productId) {
        Integer stock = inventoryService.checkStock(productId);
        return ResponseEntity.ok(stock);
    }

}
