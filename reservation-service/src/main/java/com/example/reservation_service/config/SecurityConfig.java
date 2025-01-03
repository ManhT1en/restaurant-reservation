package com.example.reservation_service.config;

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
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/home", "/error").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
            .loginPage("http://localhost:8081/login?redirect=http://localhost:8083/home")// Đặt redirect về Restaurant Service
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("http://localhost:8081/logout")
                .permitAll()
            );

        return http.build();
    }
}


