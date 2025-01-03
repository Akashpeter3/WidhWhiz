package com.app.auth.user_authentication_service.service.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.auth.user_authentication_service.dto.AuthRequest;
import com.app.auth.user_authentication_service.dto.AuthResponse;
import com.app.auth.user_authentication_service.model.User;
import com.app.auth.user_authentication_service.repository.UserRepository;
import com.app.auth.user_authentication_service.service.AuthService;
import com.app.auth.user_authentication_service.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User added successfully";
    }
    @Override
    public String authenticateAndGenerateToken(AuthRequest authRequest) {
        Map<String, Object> claims = new HashMap<>();
        return jwtUtil.generateToken(claims,authRequest.getUserName());
    }

 
}
