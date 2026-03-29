package com.rodrigoplopes.mandinha_api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record SaleItemRequestDTO(
        @NotEmpty
        String productId,
        @NotEmpty
        @Positive
        int quantity
) {}
