package com.app.apigateway.api_gateway.filter;

import com.app.apigateway.api_gateway.jwt.JwtImplementation;
import com.app.apigateway.api_gateway.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;


@Component
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private final RouteValidator validator;
    private final JwtImplementation jwtImplementation;
    private final AuthService authService;

    @Autowired
    public AuthenticationGatewayFilterFactory(RouteValidator validator, JwtImplementation jwtImplementation, @Lazy AuthService authService) {
        super(Config.class);
        this.validator = validator;
        this.jwtImplementation = jwtImplementation;
        this.authService = authService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = null;
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    // Handle missing Authorization header gracefully
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    authService.validateToken(authHeader);
                    request =  exchange.getRequest()
                    .mutate()
                    .header("username", authService.extractUserNameFromToken(authHeader))
                    .build();
                } catch (Exception e) {
                    // Handle invalid token gracefully
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access to this resource");
                }
            }
            return chain.filter(exchange.mutate().request(request).build());
            //return chain.filter(exchange);
        };
    }

    public static class Config {
        // Configuration properties (if needed)
    }
}