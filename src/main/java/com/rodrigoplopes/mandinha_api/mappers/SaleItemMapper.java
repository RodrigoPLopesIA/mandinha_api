package com.rodrigoplopes.mandinha_api.mappers;

import com.rodrigoplopes.mandinha_api.dtos.SaleItemResponseDTO;
import com.rodrigoplopes.mandinha_api.entities.SaleItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleItemMapper {

    SaleItemResponseDTO toDTO(SaleItem entity);
}