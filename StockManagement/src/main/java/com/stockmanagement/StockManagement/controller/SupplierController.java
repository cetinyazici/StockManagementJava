package com.stockmanagement.StockManagement.controller;

import com.stockmanagement.StockManagement.model.Supplier;
import com.stockmanagement.StockManagement.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.PublicKey;
import java.util.List;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {

    private final ISupplierService service;

    @Autowired
    public SupplierController(ISupplierService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public String getAllSuppliers(Model model) {
        List<Supplier> suppliers = service.getAll();
        model.addAttribute("suppliers", suppliers);
        return "suppliers/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "suppliers/add";
    }

    @PostMapping("/add")
    public String addSupplier(Supplier supplier) {
        service.create(supplier);
        return "redirect:/suppliers/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Supplier supplier = service.getById(id);
        model.addAttribute("supplier", supplier);
        return "suppliers/edit";
    }

    @PostMapping("/edit/{id}")
    public String editSupplier(@PathVariable int id, Supplier supplier) {
        supplier.setId(id);
        service.update(supplier);
        return "redirect:/suppliers/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteSupplier(@PathVariable int id) {
        service.delete(id);
        return "redirect:/suppliers/list";

    }
}

