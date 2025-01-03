package com.app.auth.user_authentication_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.auth.user_authentication_service.dto.AuthRequest;
import com.app.auth.user_authentication_service.model.User;
import com.app.auth.user_authentication_service.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  private AuthService authService;
  @Autowired
  private AuthenticationManager authenticationManager;

  @PostMapping("/addUser")
  public ResponseEntity<String> addUser(@Valid @RequestBody User user) {
    try {
      String response = authService.addUser(user);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>("User could not be added", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/welcome")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<String> welcomeUser() {
    return new ResponseEntity<>("Welcome Admin", HttpStatus.OK);
  }

  @GetMapping("/admin")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<String> welcomeAdmin() {
    return new ResponseEntity<>("Welcome Admin to the Admin Page", HttpStatus.OK);
  }

  @GetMapping("/user")
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public ResponseEntity<String> userData() {
    return new ResponseEntity<>("Welcome User", HttpStatus.OK);
  }

  @PostMapping("/authenticate")
  public ResponseEntity<String> authenticateAndGenerateToken(@Valid @RequestBody AuthRequest authRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
      if (authentication.isAuthenticated()) {
        String token = authService.authenticateAndGenerateToken(authRequest);
        return new ResponseEntity<>(token, HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
      }
    } catch (Exception e) {
      return new ResponseEntity<>("Authentication failed - Invalid username or password", HttpStatus.UNAUTHORIZED);
    }
  }
}
