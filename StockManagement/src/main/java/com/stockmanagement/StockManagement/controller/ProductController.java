package com.stockmanagement.StockManagement.controller;

import com.stockmanagement.StockManagement.dto.ProductDTO;
import com.stockmanagement.StockManagement.mapper.IProductMapper;
import com.stockmanagement.StockManagement.model.Category;
import com.stockmanagement.StockManagement.model.Supplier;
import com.stockmanagement.StockManagement.service.ICategoryService;
import com.stockmanagement.StockManagement.service.IProductService;
import com.stockmanagement.StockManagement.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;
    private final ICategoryService categoryService;
    private final ISupplierService supplierService;
    private final IProductMapper productMapper;

    @Autowired
    public ProductController(IProductService service, ICategoryService categoryService, ISupplierService supplierService, IProductMapper productMapper) {
        this.productService = service;
        this.categoryService = categoryService;
        this.supplierService = supplierService;
        this.productMapper = productMapper;
    }

    @GetMapping("/list")
    public String getAllProducts(Model model) {
        List<ProductDTO> products = productService.getAllProductDTOs();
        model.addAttribute("products", products);
        return "products/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        ProductDTO products = new ProductDTO();
        List<Category> categories = categoryService.getAll();
        List<Supplier> suppliers = supplierService.getAll();
        model.addAttribute("product", products);
        model.addAttribute("categories", categories);
        model.addAttribute("suppliers", suppliers);
        return "products/add";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") ProductDTO productDTO, Model model) {
        productService.add(productDTO);
        return "redirect:/products/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        ProductDTO productDTO = productService.getProductDTOById(id);
        List<Category> categories = categoryService.getAll();
        List<Supplier> suppliers = supplierService.getAll();
        model.addAttribute("product", productDTO);
        model.addAttribute("categories", categories);
        model.addAttribute("suppliers", suppliers);
        return "products/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable int id, @ModelAttribute("product") ProductDTO productDTO) {
        productDTO.setId(id);
        productService.updateProduct(productDTO);
        return "redirect:/products/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.delete(id);
        return "redirect:/products/list";
    }
}
