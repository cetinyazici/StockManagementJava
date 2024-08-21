package com.stockmanagement.StockManagement.service.impl;

import com.stockmanagement.StockManagement.model.Supplier;
import com.stockmanagement.StockManagement.repository.ISupplierRepository;
import com.stockmanagement.StockManagement.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierManager implements ISupplierService {
    private ISupplierRepository repository;

    @Autowired
    public SupplierManager(ISupplierRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Supplier entity) {
        repository.create(entity);
    }

    @Override
    public void update(Supplier entity) {
        repository.update(entity);
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Override
    public List<Supplier> getAll() {
        return repository.getAll();
    }

    @Override
    public Supplier getById(int id) {
        return repository.getById(id);
    }
}
