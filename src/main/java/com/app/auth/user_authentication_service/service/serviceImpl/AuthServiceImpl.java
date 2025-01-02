package com.app.auth.user_authentication_service.service.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.app.auth.user_authentication_service.dto.AuthRequest;
import com.app.auth.user_authentication_service.dto.AuthResponse;
import com.app.auth.user_authentication_service.repository.UserRepository;
import com.app.auth.user_authentication_service.service.AuthService;
import com.app.auth.user_authentication_service.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        String token = jwtUtil.generateToken(authRequest.getUserName());
        return new AuthResponse(token);
    }

    @Override
    public String authenticateAndGenerateToken(AuthRequest authRequest) {
        Map<String, Object> claims = new HashMap<>();
        return jwtUtil.generateToken(claims,authRequest.getUserName());
    }
}
