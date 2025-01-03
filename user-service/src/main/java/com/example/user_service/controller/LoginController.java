package com.example.user_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request) {
        // Lưu URL cần redirect sau khi đăng nhập (nếu có)
        String redirectUrl = request.getParameter("redirect");
        if (redirectUrl != null && !redirectUrl.isEmpty()) {
            request.getSession().setAttribute("redirect_url", redirectUrl);
        }
        return "login"; // Trả về file login.html trong thư mục templates
    }
}
