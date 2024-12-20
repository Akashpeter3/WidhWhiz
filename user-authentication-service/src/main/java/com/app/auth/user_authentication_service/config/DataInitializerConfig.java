package com.app.auth.user_authentication_service.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.app.auth.user_authentication_service.model.User;
import com.app.auth.user_authentication_service.repository.UserRepository;

@Configuration
public class DataInitializerConfig implements CommandLineRunner {

    private UserRepository userRepository;

    public DataInitializerConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            userRepository.save(new User("admin", "$2a$10$...", "ADMIN"));
            userRepository.save(new User("customer", "$2a$10$...", "CUSTOMER"));
        }
    }

}

// }
