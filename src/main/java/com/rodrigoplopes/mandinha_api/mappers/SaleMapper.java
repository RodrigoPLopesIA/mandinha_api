package com.rodrigoplopes.mandinha_api.mappers;

import com.rodrigoplopes.mandinha_api.dtos.SaleResponseDTO;
import com.rodrigoplopes.mandinha_api.entities.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring", uses = {SaleItemMapper.class})
public interface SaleMapper {

    @Mapping(target = "items", source = "items")
    SaleResponseDTO toDTO(Sale entity);
}