package com.app.apigateway.api_gateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "USER-SERVICE")
public interface WebFeignClient {

    @GetMapping("/auth/validateToken")
    String validateToken(@RequestParam String token);

}
