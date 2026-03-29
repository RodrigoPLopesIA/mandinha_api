package com.rodrigoplopes.mandinha_api.controllers;

import com.rodrigoplopes.mandinha_api.dtos.SaleRequestDTO;
import com.rodrigoplopes.mandinha_api.dtos.SaleResponseDTO;
import com.rodrigoplopes.mandinha_api.services.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService service;

    @PostMapping
    public ResponseEntity<SaleResponseDTO> create(
            @RequestBody @Valid SaleRequestDTO dto,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();
        return ResponseEntity.ok(service.create(dto, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> update(
            @PathVariable String id,
            @RequestBody @Valid SaleRequestDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<SaleResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/period")
    public ResponseEntity<Page<SaleResponseDTO>> findByPeriod(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.findByPeriod(start, end, pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SaleResponseDTO>> findByProductName(
            @RequestParam String name,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.findByProductName(name, pageable));
    }
}