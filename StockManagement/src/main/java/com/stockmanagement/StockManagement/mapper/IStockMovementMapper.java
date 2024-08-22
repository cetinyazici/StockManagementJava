package com.stockmanagement.StockManagement.mapper;

import com.stockmanagement.StockManagement.dto.StockMovementDTO;
import com.stockmanagement.StockManagement.model.StockMovement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IStockMovementMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "warehouse.id", target = "warehouseId")
    @Mapping(source = "warehouse.name", target = "warehouseName")
    @Mapping(source = "movement_date", target = "movementDate")
    StockMovementDTO toDto(StockMovement stockMovement);

    @Mapping(source = "productId", target = "product.id")
    @Mapping(source = "warehouseId", target = "warehouse.id")
    @Mapping(source = "movementDate", target = "movement_date")
    StockMovement toEntity(StockMovementDTO stockMovementDTO);


}
