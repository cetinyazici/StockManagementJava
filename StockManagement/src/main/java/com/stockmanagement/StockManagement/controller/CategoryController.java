package com.stockmanagement.StockManagement.controller;

import com.stockmanagement.StockManagement.service.ICategoryService;
import com.stockmanagement.StockManagement.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final ICategoryService service;

    @Autowired
    public CategoryController(ICategoryService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public String getAllCategories(Model model) {
        List<Category> categories = service.getAll();
        model.addAttribute("categories", categories);
        return "categories/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category());
        return "categories/add";
    }

    @PostMapping("/add")
    public String addCategory(Category category) {
        service.create(category);
        return "redirect:/categories/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Category category = service.getById(id);
        model.addAttribute("category", category);
        return "categories/edit";
    }

    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable int id, Category category) {
        category.setId(id);
        service.update(category);
        return "redirect:/categories/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        service.delete(id);
        return "redirect:/categories/list";
    }


}
