package com.stockmanagement.StockManagement.service.impl;

import com.stockmanagement.StockManagement.dto.ProductDTO;
import com.stockmanagement.StockManagement.mapper.IProductMapper;
import com.stockmanagement.StockManagement.model.Category;
import com.stockmanagement.StockManagement.model.Product;
import com.stockmanagement.StockManagement.model.Supplier;
import com.stockmanagement.StockManagement.repository.ICategoryRepository;
import com.stockmanagement.StockManagement.repository.IProductRepository;
import com.stockmanagement.StockManagement.repository.ISupplierRepository;
import com.stockmanagement.StockManagement.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductManager implements IProductService {

    private final IProductRepository productRepository;
    private IProductMapper productMapper;
    private final ICategoryRepository categoryRepository;
    private final ISupplierRepository supplierRepository;

    @Autowired
    public ProductManager(IProductRepository productRepository, IProductMapper productMapper, ICategoryRepository categoryRepository, ISupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public void add(ProductDTO productDTO) {
        Category category = categoryRepository.getById(productDTO.getCategoryId());
        Supplier supplier = supplierRepository.getById(productDTO.getSupplierId());
        if (category == null || supplier == null) {
            throw new IllegalArgumentException("Invalid category or supplier ID");
        }
        Product product = productMapper.toEntity(productDTO);
        product.setCategory(category);
        product.setSupplier(supplier);
        productRepository.create(product);
    }

    @Override
    public void updateProduct(ProductDTO productDTO) {
        Product product = productRepository.getById(productDTO.getId());
        Category category = categoryRepository.getById(productDTO.getCategoryId());
        Supplier supplier = supplierRepository.getById(productDTO.getSupplierId());

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock_quantity(productDTO.getStock_quantity());
        product.setBarcode(productDTO.getBarcode());
        product.setCategory(category);
        product.setSupplier(supplier);

        productRepository.update(product);
    }

    @Override
    public void create(Product entity) {
    }

    @Override
    public void update(Product entity) {
    }

    @Override
    public void delete(int id) {
        productRepository.delete(id);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.getAll();
    }

    @Override
    public Product getById(int id) {
        return productRepository.getById(id);
    }

    @Override
    public ProductDTO getProductDTOById(int id) {
        Product product = getById(id);
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDTO> getAllProductDTOs() {
        List<Product> products = getAll();
        return products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
