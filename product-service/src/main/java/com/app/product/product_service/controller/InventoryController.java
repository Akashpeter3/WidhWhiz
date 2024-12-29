package com.app.product.product_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.product.product_service.dto.InventoryDTO;
import com.app.product.product_service.service.InventoryService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {
    private final InventoryService inventoryService;

    // Update stock for a product
    @PutMapping("/{productId}/update-stock")
    public ResponseEntity<String> updateStock(@Valid  @PathVariable Long productId,@RequestParam int quantity) {
       InventoryDTO inventoryDTO = InventoryDTO.builder().productId(productId).build();
        inventoryService.updateStock(inventoryDTO, quantity);
        return ResponseEntity.ok("Stock updated successfully for Product ID: " + inventoryDTO.getProductId());
    }

    // Check stock for a product
    @GetMapping("/{productId}/check-stock")
    public ResponseEntity<Boolean> checkStock(@PathVariable Long productId, @RequestParam int quantity) {
        Boolean stockAvailable = inventoryService.checkStock(productId, quantity);
        return ResponseEntity.ok(stockAvailable);
    }

    // Exception handler for EntityNotFoundException
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("Entity not found", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    // Exception handler for other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("An error occurred", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
    }
}
