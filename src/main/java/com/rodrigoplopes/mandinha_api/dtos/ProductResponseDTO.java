package com.rodrigoplopes.mandinha_api.dtos;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record ProductResponseDTO(
        String id,
        String name,
        int quantity,
        BigDecimal price,
        String profileId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
