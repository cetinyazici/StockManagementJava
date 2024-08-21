package com.stockmanagement.StockManagement.repository.impl;

import com.stockmanagement.StockManagement.model.Supplier;
import com.stockmanagement.StockManagement.repository.ISupplierRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SupplierRepository extends GenericRepository<Supplier> implements ISupplierRepository {
    protected SupplierRepository() {
        super(Supplier.class);
    }
}
