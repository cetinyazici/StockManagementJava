package com.stockmanagement.StockManagement.repository.impl;

import com.stockmanagement.StockManagement.model.StockMovement;
import com.stockmanagement.StockManagement.repository.IStockMovementRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class StockMovementRepository extends GenericRepository<StockMovement> implements IStockMovementRepository {
    protected StockMovementRepository() {
        super(StockMovement.class);
    }

    @Override
    public Optional<StockMovement> getByIdOptional(int id) {
        return Optional.ofNullable(getById(id));
    }
}
