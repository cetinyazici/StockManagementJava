package com.stockmanagement.StockManagement.service.impl;

import com.stockmanagement.StockManagement.dto.StockMovementDTO;
import com.stockmanagement.StockManagement.mapper.IStockMovementMapper;
import com.stockmanagement.StockManagement.model.Product;
import com.stockmanagement.StockManagement.model.StockMovement;
import com.stockmanagement.StockManagement.model.Warehouse;
import com.stockmanagement.StockManagement.repository.IStockMovementRepository;
import com.stockmanagement.StockManagement.service.IProductService;
import com.stockmanagement.StockManagement.service.IStockMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockMovementManager implements IStockMovementService {

    private final IStockMovementRepository repository;
    private final IStockMovementMapper mapper;
    private final IProductService productService;

    @Autowired
    public StockMovementManager(IStockMovementRepository repository, IStockMovementMapper mapper, IProductService productService) {
        this.repository = repository;
        this.mapper = mapper;
        this.productService = productService;
    }

    @Override
    public void create(StockMovement entity) {
        // repository.create(entity);
    }

    @Override
    public void update(StockMovement entity) {
        //repository.update(entity);
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Override
    public List<StockMovement> getAll() {
        return repository.getAll();
    }

    @Override
    public StockMovement getById(int id) {
        return repository.getById(id);
    }

    @Override
    public long count() {
        return (int) repository.count();
    }

    @Override
    public void createStockMovement(StockMovementDTO stockMovementDTO) {
        StockMovement stockMovement = mapper.toEntity(stockMovementDTO);

        Product product = productService.getById(stockMovementDTO.getProductId());
        int currentStock = product.getStock_quantity();
        int movementQuantity = stockMovementDTO.getQuantity();
        String movementType = stockMovementDTO.getMovementType();

        if ("IN".equals(movementType)) {
            product.setStock_quantity(currentStock + movementQuantity);
        } else if ("OUT".equals(movementType)) {
            product.setStock_quantity(currentStock - movementQuantity);
        }
        productService.update(product);
        repository.create(stockMovement);
    }

    @Override
    public void updateStockMovement(StockMovementDTO stockMovementDTO) {
        // Mevcut stok hareketini al
        Optional<StockMovement> existingStockMovementOpt = repository.getByIdOptional(stockMovementDTO.getId());

        if (existingStockMovementOpt.isPresent()) {
            StockMovement existingStockMovement = existingStockMovementOpt.get();
            int oldQuantity = existingStockMovement.getQuantity();
            int newQuantity = stockMovementDTO.getQuantity();
            String movementType = stockMovementDTO.getMovementType();

            Product product = productService.getById(stockMovementDTO.getProductId());
            int currentStock = product.getStock_quantity();

            if ("IN".equals(movementType)) {
                product.setStock_quantity(currentStock + newQuantity);
            } else if ("OUT".equals(movementType)) {
                product.setStock_quantity(currentStock - newQuantity);
            }
            productService.update(product);
            repository.update(mapper.toEntity(stockMovementDTO));
        }
    }


    @Override
    public List<StockMovementDTO> getAllStockMovement() {
        return repository.getAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public StockMovementDTO getByIdStockMovement(int id) {
        StockMovement stockMovement = repository.getById(id);
        return stockMovement != null ? mapper.toDto(stockMovement) : null;
    }

    @Override
    public Map<Warehouse, Integer> getStockByWarehouse() {
        List<StockMovement> movements = repository.getAll();
        Map<Warehouse, Integer> warehouseStock = new HashMap<>();

        for (StockMovement movement : movements) {
            Warehouse warehouse = movement.getWarehouse();
            Product product = movement.getProduct();
            int quantity = movement.getQuantity();
            String movementType = movement.getMovementType();

            warehouseStock.put(warehouse, warehouseStock.getOrDefault(warehouse, 0) + quantity);
        }

        return warehouseStock;
    }

}
