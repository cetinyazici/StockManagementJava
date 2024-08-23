package com.stockmanagement.StockManagement.repository;

import com.stockmanagement.StockManagement.model.Product;

import java.util.List;

public interface IProductRepository extends IGenericRepository<Product> {
    long getTotalStock();
    List<Product> findByStockQuantityLessThanEqual(int threshold);
}
