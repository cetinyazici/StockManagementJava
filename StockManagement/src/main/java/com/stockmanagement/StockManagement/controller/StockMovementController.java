package com.stockmanagement.StockManagement.controller;

import com.stockmanagement.StockManagement.dto.ProductDTO;
import com.stockmanagement.StockManagement.dto.StockMovementDTO;
import com.stockmanagement.StockManagement.model.Warehouse;
import com.stockmanagement.StockManagement.service.IProductService;
import com.stockmanagement.StockManagement.service.IStockMovementService;
import com.stockmanagement.StockManagement.service.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return "stockmovements/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        StockMovementDTO stockMovementDTO = new StockMovementDTO();
        List<ProductDTO> products = productService.getAllProductDTOs();
        List<Warehouse> warehouses = warehouseService.getAll();

        model.addAttribute("stockMovement", stockMovementDTO);
        model.addAttribute("products", products);
        model.addAttribute("warehouses", warehouses);
        return "stockmovements/add";
    }

    @PostMapping("/add")
    public String addStockMovement(@ModelAttribute("stockMovement") StockMovementDTO stockMovementDTO) {
        stockMovementService.createStockMovement(stockMovementDTO);
        return "redirect:/stockmovements/list";
    }

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

        return "stockmovements/edit";
    }


    @PostMapping("/edit/{id}")
    public String updateStockMovement(@PathVariable int id, @ModelAttribute("stockMovement") StockMovementDTO stockMovementDTO) {
        stockMovementDTO.setId(id);
        stockMovementService.updateStockMovement(stockMovementDTO);
        return "redirect:/stockmovements/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteStockMovement(@PathVariable int id) {
        stockMovementService.delete(id);
        return "redirect:/stockmovements/list";
    }


}
