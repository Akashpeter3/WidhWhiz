package com.app.apigateway.api_gateway.service;

import com.app.apigateway.api_gateway.client.WebFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final WebFeignClient webFeignClient;
    @Autowired
    public AuthService(WebFeignClient webFeignClient) {
        this.webFeignClient = webFeignClient;
    }

    public void validateToken(String token) {
        webFeignClient.validateToken(token);
    }

    public String extractUserNameFromToken(String token) {
        return webFeignClient.extractUserNameFromToken(token);
    }
}
