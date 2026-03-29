package com.rodrigoplopes.mandinha_api.mappers;


import com.rodrigoplopes.mandinha_api.dtos.SaleResponseDTO;
import com.rodrigoplopes.mandinha_api.entities.Sale;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SaleItemMapper.class})
public interface SaleMapper {

    SaleResponseDTO toDTO(Sale entity);
}