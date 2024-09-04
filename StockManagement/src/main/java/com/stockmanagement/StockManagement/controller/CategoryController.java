package com.stockmanagement.StockManagement.controller;

import com.stockmanagement.StockManagement.service.ICategoryService;
import com.stockmanagement.StockManagement.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
        model.addAttribute("pageTitle", "Categories List");
        model.addAttribute("pageContent", "categories/list");
        return "layout";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("pageTitle", "Categories Add");
        model.addAttribute("pageContent", "categories/add");
        return "layout";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String addCategory(Category category) {
        service.create(category);
        return "redirect:/categories/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Category category = service.getById(id);
        model.addAttribute("category", category);
        model.addAttribute("pageTitle", "Categories Edit");
        model.addAttribute("pageContent", "categories/edit");
        return "layout";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable int id, Category category) {
        category.setId(id);
        service.update(category);
        return "redirect:/categories/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        service.delete(id);
        return "redirect:/categories/list";
    }


}
