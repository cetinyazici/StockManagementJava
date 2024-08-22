package com.stockmanagement.StockManagement.repository.impl;

import com.stockmanagement.StockManagement.model.Product;
import com.stockmanagement.StockManagement.repository.IProductRepository;
import com.stockmanagement.StockManagement.service.IProductService;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository extends GenericRepository<Product> implements IProductRepository {
    protected ProductRepository() {
        super(Product.class);
    }
}
