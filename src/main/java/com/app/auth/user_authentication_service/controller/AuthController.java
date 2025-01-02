package com.app.auth.user_authentication_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.auth.user_authentication_service.dto.AuthRequest;
import com.app.auth.user_authentication_service.dto.AuthResponse;
import com.app.auth.user_authentication_service.service.AuthService;
import com.netflix.discovery.converters.Auto;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    
  
    
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @GetMapping("/welcome")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String welcomeUser() {
      return "Welcome Admin";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String welcomeAdmin() {
      return "Welcome Admin to the Admin Page";
    }
    
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userData() {
        return "Welcome User";
    }


    @PostMapping("/authenticate")
    public String authenticateAndGenerateToken(@Valid @RequestBody AuthRequest authRequest) {
        
      Authentication authentication =  authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
      if (authentication.isAuthenticated()) {
          return authService.authenticateAndGenerateToken(authRequest);

      } else {
        throw new UsernameNotFoundException("Invalid username or password");
      }
    }
}
