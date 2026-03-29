package com.rodrigoplopes.mandinha_api.dtos;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


public record SaleResponseDTO(
        String id,
        String profileId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<SaleItemResponseDTO> items,
        BigDecimal totalAmount
) {}