package com.stockmanagement.StockManagement.repository.impl;

import com.stockmanagement.StockManagement.repository.ICategoryRepository;
import com.stockmanagement.StockManagement.model.Category;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CategoryRepository extends GenericRepository<Category> implements ICategoryRepository {
    public CategoryRepository() {
        super(Category.class);
    }
}
