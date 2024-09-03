package com.stockmanagement.StockManagement.controller;

import com.stockmanagement.StockManagement.model.User;
import com.stockmanagement.StockManagement.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/detail")
    public String viewProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userService.findByUsername(username);
        model.addAttribute("user", user.orElse(null));
        return "profile/detail";
    }

    @GetMapping("/edit")
    public String editProfileForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userService.findByUsername(username);
        model.addAttribute("user", user.orElse(null));
        return "profile/edit";
    }

    @PostMapping("/edit")
    public String editProfile(@ModelAttribute User user, Model model) {
        // Kullanıcının kimliğini kontrol et
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> existingUser = userService.findByUsername(username);

        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setName(user.getName());
            updatedUser.setPassword(user.getPassword()); // Parola şifreleme gerekli olabilir

            userService.save(updatedUser);
            model.addAttribute("user", updatedUser);
        }

        return "redirect:/profile/detail";
    }
}
