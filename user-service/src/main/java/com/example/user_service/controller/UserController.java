package com.example.user_service.controller;

import com.example.user_service.entity.User;
import com.example.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // [1] Đăng ký tài khoản
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Kiểm tra trùng username
        Optional<User> found = userRepository.findByUsername(user.getUsername());
        if (found.isPresent()) {
            return ResponseEntity.badRequest().body("Username already taken!");
        }
        // Lưu user (chưa mã hóa password => tùy bạn bổ sung)
        userRepository.save(user);
        return ResponseEntity.ok("Register success!");
    }

    // [2] Đăng nhập (giản lược)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        Optional<User> found = userRepository.findByUsername(loginUser.getUsername());
        if (found.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found!");
        }
        User user = found.get();
        // So sánh password (chưa mã hóa => ví dụ)
        if (!user.getPassword().equals(loginUser.getPassword())) {
            return ResponseEntity.badRequest().body("Wrong password!");
        }
        // Trả về "fake JWT token" hoặc tùy bạn xử lý
        return ResponseEntity.ok("Fake-JWT-Token-For-" + user.getUsername());
    }

    // [3] Lấy danh sách users
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}
