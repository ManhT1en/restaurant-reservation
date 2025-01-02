package com.example.user_service.config;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.user_service.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) 
            throws UsernameNotFoundException {
        com.example.user_service.entity.User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // user.getPassword() là "2610" (raw text)
        // => prepend {noop} hoặc dùng NoOpPasswordEncoder
        return User
            .withUsername(user.getUsername())
            .password(user.getPassword()) // <-- password raw
            .roles("USER")
            .build();
    }
}

