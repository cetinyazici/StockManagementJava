package com.stockmanagement.StockManagement.controller;

import com.stockmanagement.StockManagement.model.Product;
import com.stockmanagement.StockManagement.model.StockMovement;
import com.stockmanagement.StockManagement.model.Warehouse;
import com.stockmanagement.StockManagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("dashboards")
public class DashboardController {

    private final IProductService productService;
    private final ISupplierService supplierService;
    private final IWarehouseService warehouseService;
    private final IStockMovementService stockMovementService;

    @Autowired
    public DashboardController(IProductService productService, ISupplierService supplierService, IWarehouseService warehouseService, IStockMovementService stockMovementService) {
        this.productService = productService;
        this.supplierService = supplierService;
        this.warehouseService = warehouseService;
        this.stockMovementService = stockMovementService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/index")
    public String index(Model model) {
        long totalProducts = productService.count();
        long totalSuppliers = supplierService.count();
        long totalWarehouses = warehouseService.count();
        long totalStocks = productService.getTotalStock();
        List<Product> criticalStockLevels = productService.findByStockQuantityLessThanEqual(10);
        Map<Warehouse, Integer> stockByWarehouse = stockMovementService.getStockByWarehouse();


        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("totalSuppliers", totalSuppliers);
        model.addAttribute("totalWarehouses", totalWarehouses);
        model.addAttribute("totalStocks", totalStocks);
        model.addAttribute("criticalStockLevels", criticalStockLevels);
        model.addAttribute("stockByWarehouse", stockByWarehouse);
        return "dashboards/index";
    }
}
