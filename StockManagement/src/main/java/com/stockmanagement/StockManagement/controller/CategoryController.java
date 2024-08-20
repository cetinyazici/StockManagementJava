package com.stockmanagement.StockManagement.controller;

import com.stockmanagement.StockManagement.service.ICategoryService;
import com.stockmanagement.StockManagement.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String getAllCategories(Model model){
        List<Category> categories = service.getAll();
        model.addAttribute("categories", categories);
        return "categories/list";
    }
}
