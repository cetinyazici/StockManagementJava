package com.stockmanagement.StockManagement.controller;

import com.stockmanagement.StockManagement.dto.CreateUserRequest;
import com.stockmanagement.StockManagement.model.Role;
import com.stockmanagement.StockManagement.service.impl.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Kullanıcı kayıt formunu gösterir
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("roles", Role.values()); // Tüm rollerin listesini alır
        return "auth/register";
    }

    // Kullanıcı kayıt işlemi
    @PostMapping("/register")
    public String registerUser(CreateUserRequest request) {
        userService.createUser(request);
        return "redirect:/auth/login"; // Başarıyla kayıt olduktan sonra giriş sayfasına yönlendirme
    }

    // Giriş formunu gösterir
    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    // Giriş işlemi
    @PostMapping("/login")
    public String loginUser(String username, String password) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/dashboards/index"; // Başarılı giriş sonrası yönlendirme
        } else {
            return "redirect:/auth/login?error=true"; // Giriş hatası varsa, hata mesajıyla geri dön
        }
    }
}
