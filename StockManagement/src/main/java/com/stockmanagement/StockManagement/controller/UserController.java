package com.stockmanagement.StockManagement.controller;

import com.stockmanagement.StockManagement.dto.CreateUserRequest;
import com.stockmanagement.StockManagement.model.Category;
import com.stockmanagement.StockManagement.model.User;
import com.stockmanagement.StockManagement.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Listeleme sayfasını döndürür
    @GetMapping("/list")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/list";
    }

    // Kullanıcı düzenleme sayfasını döndürür
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "users/edit";
    }

    // Kullanıcıyı günceller
    @PostMapping("/edit")
    public String updateUser(@ModelAttribute User user) {
        userService.createUser(new CreateUserRequest(
                user.getName(),
                user.getUsername(),
                user.getPassword(), // Şifreyi burada şifrelemek istemiyorsanız, şifreleme `UserService` içinde yapılır.
                user.getAuthorities()
        ));
        return "redirect:/users/list";
    }

    // Kullanıcıyı siler
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/users/list";
    }
}
