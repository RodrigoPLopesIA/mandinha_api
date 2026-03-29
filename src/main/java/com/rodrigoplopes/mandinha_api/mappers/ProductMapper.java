package com.rodrigoplopes.mandinha_api.mappers;

import com.rodrigoplopes.mandinha_api.dtos.ProductRequestDTO;
import com.rodrigoplopes.mandinha_api.dtos.ProductResponseDTO;
import com.rodrigoplopes.mandinha_api.entities.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profileId", ignore = true)
    @Mapping(target = "saleItems", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toEntity(ProductRequestDTO dto);

    ProductResponseDTO toDTO(Product entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profileId", ignore = true)
    @Mapping(target = "saleItems", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(ProductRequestDTO dto, @MappingTarget Product entity);
}