package com.rodrigoplopes.mandinha_api.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record SaleRequestDTO(

        @NotEmpty(message = "Sale must have at least one item")
        @Valid
        List<SaleItemRequestDTO> items

) {}