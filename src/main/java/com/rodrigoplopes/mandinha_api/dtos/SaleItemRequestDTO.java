package com.rodrigoplopes.mandinha_api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record SaleItemRequestDTO(
        @NotNull
        String productId,
        @NotNull
        @Positive
        int quantity
) {}
