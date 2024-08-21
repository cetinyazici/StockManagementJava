package com.stockmanagement.StockManagement.repository.impl;

import com.stockmanagement.StockManagement.model.Warehouse;
import com.stockmanagement.StockManagement.repository.IWarehouseRepository;
import org.springframework.stereotype.Repository;

@Repository
public class WarehouseRepositoty extends GenericRepository<Warehouse> implements IWarehouseRepository {
    protected WarehouseRepositoty() {
        super(Warehouse.class);
    }
}
