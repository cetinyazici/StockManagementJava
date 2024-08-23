package com.stockmanagement.StockManagement.service;

import com.stockmanagement.StockManagement.dto.StockMovementDTO;
import com.stockmanagement.StockManagement.model.Product;
import com.stockmanagement.StockManagement.model.StockMovement;
import com.stockmanagement.StockManagement.model.Warehouse;

import java.util.List;
import java.util.Map;

public interface IStockMovementService extends IGenericService<StockMovement> {
    void createStockMovement(StockMovementDTO stockMovementDTO);

    void updateStockMovement(StockMovementDTO stockMovementDTO);

    List<StockMovementDTO> getAllStockMovement();

    StockMovementDTO getByIdStockMovement(int id);
    public Map<Warehouse, Integer> getStockByWarehouse();

}
