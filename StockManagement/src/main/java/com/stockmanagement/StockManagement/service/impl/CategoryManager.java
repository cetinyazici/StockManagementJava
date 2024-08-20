package com.stockmanagement.StockManagement.service.impl;

import com.stockmanagement.StockManagement.service.ICategoryService;
import com.stockmanagement.StockManagement.repository.ICategoryRepository;
import com.stockmanagement.StockManagement.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryManager implements ICategoryService {

    private ICategoryRepository repository;

    @Autowired
    public CategoryManager(ICategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Category entity) {
        repository.create(entity);
    }

    @Override
    public void update(Category entity) {
repository.update(entity);
    }

    @Override
    public void delete(int id) {
repository.delete(id);
    }

    @Override
    public List<Category> getAll() {
        return repository.getAll();
    }

    @Override
    public Category getById(int id) {
        return repository.getById(id);
    }
}
