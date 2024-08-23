package com.stockmanagement.StockManagement.service;

import com.stockmanagement.StockManagement.dto.ProductDTO;
import com.stockmanagement.StockManagement.model.Product;

import java.util.List;

public interface IProductService extends IGenericService<Product>{
    void add(ProductDTO productDTO);
    void updateProduct(ProductDTO productDTO);
    ProductDTO getProductDTOById(int id);
    List<ProductDTO> getAllProductDTOs();
    long getTotalStock();
    List<Product> findByStockQuantityLessThanEqual(int threshold);
}
