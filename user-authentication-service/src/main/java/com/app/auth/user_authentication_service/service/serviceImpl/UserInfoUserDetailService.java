package com.app.auth.user_authentication_service.service.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.app.auth.user_authentication_service.config.UserToUserDetails;
import com.app.auth.user_authentication_service.model.User;
import com.app.auth.user_authentication_service.repository.UserRepository;

@Component
public class UserInfoUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User>userOptional = userRepository.findByUsername(username);
    //convert User -> UserDetails;    
   return userOptional.map(UserToUserDetails::new)
    .orElseThrow(()->new UsernameNotFoundException("User Not Found"));
    }
}
