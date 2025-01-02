package com.example.restaurant_service.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated() // Bắt buộc đăng nhập cho mọi endpoint
            )
            .formLogin(login -> login
                .loginPage("http://localhost:8081/login") // Chuyển hướng tới User Service
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("http://localhost:8081/logout") // Chuyển hướng tới logout ở User Service
                .permitAll()
            );
        return http.build();
    }
}


