package com.stockmanagement.StockManagement.service.impl;

import com.stockmanagement.StockManagement.model.Warehouse;
import com.stockmanagement.StockManagement.repository.IWarehouseRepository;
import com.stockmanagement.StockManagement.service.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseManager implements IWarehouseService {

    private final IWarehouseRepository repository;

    @Autowired
    public WarehouseManager(IWarehouseRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Warehouse entity) {
        repository.create(entity);
    }

    @Override
    public void update(Warehouse entity) {
        repository.update(entity);
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Override
    public List<Warehouse> getAll() {
        return repository.getAll();
    }

    @Override
    public Warehouse getById(int id) {
        return repository.getById(id);
    }

    @Override
    public long count() {
        return (int) repository.count();
    }
}
