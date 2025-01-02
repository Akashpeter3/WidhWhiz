package com.app.auth.user_authentication_service.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.auth.user_authentication_service.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    

}
