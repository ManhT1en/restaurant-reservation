package com.example.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/error").permitAll() // Cho phép không xác thực với /login và /error
                .anyRequest().authenticated() // Các URL khác yêu cầu đăng nhập
            )
            .formLogin(login -> login
                .loginPage("/login") // Trang login tùy chỉnh
                .defaultSuccessUrl("/home", true) // Chuyển hướng mặc định sau khi login thành công
                .permitAll() // Không yêu cầu xác thực tại /login
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout") // Chuyển hướng khi logout thành công
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // Sử dụng mật khẩu không mã hóa
    }
}
