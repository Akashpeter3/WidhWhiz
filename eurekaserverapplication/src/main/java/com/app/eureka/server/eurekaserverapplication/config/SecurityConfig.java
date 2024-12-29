package com.app.eureka.server.eurekaserverapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/eureka/**")  // Ignore CSRF for Eureka clients
            )
            .authorizeHttpRequests(auth -> auth
                // Permit all Eureka client operations without authentication
                .requestMatchers("/eureka/**").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                // Require authentication only for dashboard access
                .requestMatchers("/").authenticated()
                .requestMatchers("/lastn/**").authenticated()
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                // Allow other endpoints
                .anyRequest().permitAll()
            )
            // Only apply form login to dashboard URLs
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessHandler(customLogoutSuccessHandler())
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .sessionManagement(session -> session
                .invalidSessionUrl("/login?expired")
                .maximumSessions(1)
                .expiredUrl("/login?expired")
            );

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        return new InMemoryUserDetailsManager(
            User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin123")
                .roles("ADMIN")
                .build()
        );
    }

    @Bean
    public LogoutSuccessHandler customLogoutSuccessHandler() {
        return (HttpServletRequest request, HttpServletResponse response, 
                org.springframework.security.core.Authentication authentication) -> {
            response.sendRedirect("/login?logout");
        };
    }
}