package com.stockmanagement.StockManagement.mapper;

import com.stockmanagement.StockManagement.dto.ProductDTO;
import com.stockmanagement.StockManagement.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IProductMapper {
    // Listeleme için DTO
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "supplier.name", target = "supplierName")
    ProductDTO toDto(Product product);

    // Ekleme ve güncelleme için DTO
    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "supplierId", target = "supplier.id")
    Product toEntity(ProductDTO productDTO);
}
