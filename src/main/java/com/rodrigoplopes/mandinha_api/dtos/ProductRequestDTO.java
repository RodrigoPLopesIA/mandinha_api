package com.rodrigoplopes.mandinha_api.dtos;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductRequestDTO(
        @NotEmpty
        String name,
        @NotNull
        @Positive
        int quantity,
        @NotNull
        @Positive
        BigDecimal price
) {}