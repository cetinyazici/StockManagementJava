package com.stockmanagement.StockManagement.repository;

import com.stockmanagement.StockManagement.model.StockMovement;

import java.util.Optional;

public interface IStockMovementRepository extends IGenericRepository<StockMovement> {
    Optional<StockMovement> getByIdOptional(int id);
}
