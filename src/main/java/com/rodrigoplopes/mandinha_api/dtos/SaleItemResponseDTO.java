package com.rodrigoplopes.mandinha_api.dtos;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record SaleItemResponseDTO(
        String id,
        String productId,
        String productName,
        int quantity,
        BigDecimal price
) {}
