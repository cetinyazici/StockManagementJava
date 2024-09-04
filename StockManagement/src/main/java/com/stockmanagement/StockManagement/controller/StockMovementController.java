package com.stockmanagement.StockManagement.controller;

import com.stockmanagement.StockManagement.dto.ProductDTO;
import com.stockmanagement.StockManagement.dto.StockMovementDTO;
import com.stockmanagement.StockManagement.model.Warehouse;
import com.stockmanagement.StockManagement.service.IProductService;
import com.stockmanagement.StockManagement.service.IStockMovementService;
import com.stockmanagement.StockManagement.service.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/stockmovements")
public class StockMovementController {
    private final IStockMovementService stockMovementService;
    private final IProductService productService;
    private final IWarehouseService warehouseService;

    @Autowired
    public StockMovementController(IStockMovementService stockMovementService, IProductService productService, IWarehouseService warehouseService) {
        this.stockMovementService = stockMovementService;
        this.productService = productService;
        this.warehouseService = warehouseService;
    }

    @GetMapping("/list")
    public String getAllStockMovements(Model model) {
        List<StockMovementDTO> stockMovements = stockMovementService.getAllStockMovement();
        model.addAttribute("stockMovements", stockMovements);
        model.addAttribute("pageTitle", "Stockmovement List");
        model.addAttribute("pageContent", "stockmovements/list");
        return "layout";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    public String showAddForm(Model model) {
        StockMovementDTO stockMovementDTO = new StockMovementDTO();
        List<ProductDTO> products = productService.getAllProductDTOs();
        List<Warehouse> warehouses = warehouseService.getAll();

        model.addAttribute("stockMovement", stockMovementDTO);
        model.addAttribute("products", products);
        model.addAttribute("warehouses", warehouses);
        model.addAttribute("pageTitle", "Stockmovement Add");
        model.addAttribute("pageContent", "stockmovements/add");
        return "layout";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String addStockMovement(@ModelAttribute("stockMovement") StockMovementDTO stockMovementDTO) {
        stockMovementService.createStockMovement(stockMovementDTO);
        return "redirect:/stockmovements/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        StockMovementDTO stockMovementDTO = stockMovementService.getByIdStockMovement(id);
        List<ProductDTO> products = productService.getAllProductDTOs();
        List<Warehouse> warehouses = warehouseService.getAll();
        model.addAttribute("stockMovement", stockMovementDTO);
        model.addAttribute("products", products);
        model.addAttribute("warehouses", warehouses);

        int productId = stockMovementDTO.getProductId();
        ProductDTO productDTO = productService.getProductDTOById(productId);
        model.addAttribute("productQuantity", productDTO.getStock_quantity());

        // Layout-specific attributes
        model.addAttribute("title", "Edit Stock Movement");
        model.addAttribute("header", "Edit Stock Movement");
        model.addAttribute("pageTitle", "Stockmovement Edit");
        model.addAttribute("pageContent", "stockmovements/edit");
        return "layout";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit/{id}")
    public String updateStockMovement(@PathVariable int id, @ModelAttribute("stockMovement") StockMovementDTO stockMovementDTO) {
        stockMovementDTO.setId(id);
        stockMovementService.updateStockMovement(stockMovementDTO);
        return "redirect:/stockmovements/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteStockMovement(@PathVariable int id) {
        stockMovementService.delete(id);
        return "redirect:/stockmovements/list";
    }


}
