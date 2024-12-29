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
                .ignoringRequestMatchers("/eureka/**")  // Allow Eureka clients to bypass CSRF
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()  // Permit static assets and login
                .requestMatchers("/eureka/**").permitAll()  // Allow Eureka clients to register without auth
                .anyRequest().authenticated()  // Secure all other endpoints
            )
            .formLogin(form -> form
                .loginPage("/login")  // Custom login page
                .defaultSuccessUrl("/", true)  // Redirect to home page after login
                .failureUrl("/login?error")  // Redirect with error param on failed login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))  // Logout on /logout path
                .logoutSuccessHandler(customLogoutSuccessHandler())  // Custom logout handler
                .invalidateHttpSession(true)  // Invalidate session on logout
                .deleteCookies("JSESSIONID")  // Clear session cookies
                .permitAll()
            )
            .sessionManagement(session -> session
                .invalidSessionUrl("/login?expired")  // Redirect to login if session expires
                .maximumSessions(1)  // Allow only one session per user
                .expiredUrl("/login?expired")  // Redirect if session expires
            );

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        return new InMemoryUserDetailsManager(
            User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin123")
                .roles("USER", "ADMIN")  // Assign admin role
                .build()
        );
    }

    @Bean
    public LogoutSuccessHandler customLogoutSuccessHandler() {
        return (HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.Authentication authentication) -> {
            response.sendRedirect("/login?logout");  // Redirect to login after logout
        };
    }
}