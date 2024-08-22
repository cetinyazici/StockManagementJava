package com.stockmanagement.StockManagement.service;

import com.stockmanagement.StockManagement.dto.StockMovementDTO;
import com.stockmanagement.StockManagement.model.StockMovement;

import java.util.List;

public interface IStockMovementService extends IGenericService<StockMovement> {
    void createStockMovement(StockMovementDTO stockMovementDTO);

    void updateStockMovement(StockMovementDTO stockMovementDTO);

    List<StockMovementDTO> getAllStockMovement();

    StockMovementDTO getByIdStockMovement(int id);
}
