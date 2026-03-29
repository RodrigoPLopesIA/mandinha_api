package com.rodrigoplopes.mandinha_api.mappers;

import com.rodrigoplopes.mandinha_api.dtos.ProductRequestDTO;
import com.rodrigoplopes.mandinha_api.dtos.ProductResponseDTO;
import com.rodrigoplopes.mandinha_api.entities.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {


    Product toEntity(ProductRequestDTO dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "profileId", source = "profileId")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    ProductResponseDTO toDTO(Product entity);

    void updateEntityFromDTO(ProductRequestDTO dto, @MappingTarget Product entity);
}