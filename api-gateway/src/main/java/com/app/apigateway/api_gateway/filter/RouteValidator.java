package com.app.apigateway.api_gateway.filter;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {

    public static final List<String> OPEN_API_ENDPOINTS = Arrays.asList(
        "/auth/register",
        "/auth/token",
            "/auth/eureka"
        
            
    );



    public Predicate<ServerHttpRequest> isSecured = 
        request -> OPEN_API_ENDPOINTS.stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));                
}

