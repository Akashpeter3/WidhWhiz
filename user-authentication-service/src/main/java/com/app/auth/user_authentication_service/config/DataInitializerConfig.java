package com.app.auth.user_authentication_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.auth.user_authentication_service.model.User;
import com.app.auth.user_authentication_service.repository.UserRepository;

@Configuration
public class DataInitializerConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializerConfig(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Initialize SUPER_ADMIN if not present
        if (userRepository.findByUsername("Akash").isEmpty()) {
            User superAdmin = new User(
                "Akash",
                passwordEncoder.encode("Akash123"),
                "akashpeter3@gmail.com",
                "ROLE_SUPER_ADMIN"
            );
            userRepository.save(superAdmin);
            System.out.println("SUPER_ADMIN user created.");
        } else {
            System.out.println("SUPER_ADMIN already exists.");
        }

        // Initialize ADMIN and USER if not already present
        // if (userRepository.findByUsername("admin").isEmpty()) {
        //     User admin = new User(
        //         "Arun",
        //         passwordEncoder.encode("a@123"),
        //         "admin@gmail.com",
        //         "ROLE_ADMIN"
        //     );
        //     userRepository.save(admin);
        //     System.out.println("ADMIN user created.");
        // }

        // if (userRepository.findByUsername("user").isEmpty()) {
        //     User user = new User(
        //         "user",
        //         passwordEncoder.encode("user@123"),
        //         "user@gmail.com",
        //         "ROLE_USER"
        //     );
        //     userRepository.save(user);
        //     System.out.println("USER created.");
        // }
    }
}