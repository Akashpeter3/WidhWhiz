package com.app.order.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

    @GetMapping("/inventory/{productId}/check-stock")
    Boolean checkStock(@PathVariable Long productId, @RequestParam int quantity);

    @PutMapping("/inventory/{productId}/update-stock")
    void updateStock(@PathVariable Long productId, @RequestParam int quantity);



}
