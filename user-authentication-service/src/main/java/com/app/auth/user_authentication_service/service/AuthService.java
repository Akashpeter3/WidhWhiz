package com.app.auth.user_authentication_service.service;

import com.app.auth.user_authentication_service.dto.AuthRequest;
import com.app.auth.user_authentication_service.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest authRequest);
}
