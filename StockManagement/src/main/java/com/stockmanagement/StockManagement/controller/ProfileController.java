package com.stockmanagement.StockManagement.controller;

import com.stockmanagement.StockManagement.model.User;
import com.stockmanagement.StockManagement.service.impl.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public ProfileController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/detail")
    public String viewProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userService.findByUsername(username);
        model.addAttribute("user", user.orElse(null));
        model.addAttribute("pageTitle", "Profile");
        model.addAttribute("pageContent", "profile/detail");
        return "layout";
    }

    @GetMapping("/edit")
    public String editProfileForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userService.findByUsername(username);
        model.addAttribute("user", user.orElse(null));
        model.addAttribute("pageTitle", "Profile Edit");
        model.addAttribute("pageContent", "profile/edit");
        return "layout";
    }

    @PostMapping("/edit")
    public String editProfile(@ModelAttribute User user, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, Model model) {
        boolean isUpdated = userService.updateProfile(user, oldPassword, newPassword);
        if (isUpdated) {
            return "redirect:/profile/detail";
        } else {
            model.addAttribute("error", "Eski şifre yanlış!");
            model.addAttribute("user", user);
            return "profile/edit";
        }
    }
}
