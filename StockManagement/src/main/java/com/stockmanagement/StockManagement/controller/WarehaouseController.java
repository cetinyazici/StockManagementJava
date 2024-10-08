package com.stockmanagement.StockManagement.controller;

import com.stockmanagement.StockManagement.model.Warehouse;
import com.stockmanagement.StockManagement.service.IWarehouseService;
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
@RequestMapping("/warehouses")
public class WarehaouseController {

    private final IWarehouseService service;

    @Autowired
    public WarehaouseController(IWarehouseService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public String getAllWarehouse(Model model) {
        List<Warehouse> warehouses = service.getAll();
        model.addAttribute("warehouses", warehouses);
        model.addAttribute("pageTitle", "Warehouse List");
        model.addAttribute("pageContent", "warehouses/list");
        return "layout";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("warehouse", new Warehouse());
        model.addAttribute("pageTitle", "Warehouse Add");
        model.addAttribute("pageContent", "warehouses/add");
        return "layout";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String addWarehouse(Warehouse warehouse) {
        service.create(warehouse);
        return "redirect:/warehouses/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Warehouse warehouse = service.getById(id);
        model.addAttribute("warehouse", warehouse);
        model.addAttribute("pageTitle", "Warehouse Edit");
        model.addAttribute("pageContent", "warehouses/edit");
        return "layout";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Warehouse warehouse) {
        warehouse.setId(id);
        service.update(warehouse);
        return "redirect:/warehouses/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteWarehouse(@PathVariable int id) {
        service.delete(id);
        return "redirect:/warehouses/list";
    }

}
