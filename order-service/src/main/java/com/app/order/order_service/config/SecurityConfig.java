// package com.app.order.order_service.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;

// public class SecurityConfig {
//  @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/order/**").permitAll()  // Allow public access to order endpoints
//                 .anyRequest().authenticated()
//             )
//             .csrf().disable();  // Disable CSRF for testing (Optional, depends on your requirements)
        
//         return http.build();
//     }
// }
