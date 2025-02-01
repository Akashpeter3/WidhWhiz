package com.app.auth.user_authentication_service.service;

import com.app.auth.user_authentication_service.dto.AuthRequest;

import com.app.auth.user_authentication_service.model.User;

public interface AuthService {

    String addUser (User user);
    String authenticateAndGenerateToken(AuthRequest authRequest);
    public void validateToken(String token, String username);
    public void validateToken(String token);
}
