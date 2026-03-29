package com.rodrigoplopes.mandinha_api.mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.rodrigoplopes.mandinha_api.dtos.SaleItemResponseDTO;
import com.rodrigoplopes.mandinha_api.entities.SaleItem;

@Mapper(componentModel = "spring")
public interface SaleItemMapper {

    @Mapping(target = "productName", source = "product.name")
    SaleItemResponseDTO toDTO(SaleItem entity);
}