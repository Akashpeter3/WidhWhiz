// package com.app.auth.user_authentication_service.config;


// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import com.app.auth.user_authentication_service.model.User;
// import com.app.auth.user_authentication_service.repository.UserRepository;

// @Configuration
// public class DataInitializerConfig implements CommandLineRunner {

//     private final UserRepository userRepository;
//     private final PasswordEncoder passwordEncoder;

//     public DataInitializerConfig(UserRepository userRepository,PasswordEncoder passwordEncoder) {
//         this.userRepository = userRepository;
//         this.passwordEncoder = passwordEncoder;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         if (userRepository.count() == 0) {
//             passwordEncoder.encode("admin@123");
//             userRepository.save(new User("admin", passwordEncoder.encode("admin@123"),"admin@gmail.com", "ADMIN"));
//             userRepository.save(new User("user", passwordEncoder.encode("user@123"),"user@gmail.com", "USER"));
//         }
//     }

// }

// // }
