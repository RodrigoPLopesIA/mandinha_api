package com.rodrigoplopes.mandinha_api.controllers;

import com.rodrigoplopes.mandinha_api.dtos.ProductRequestDTO;
import com.rodrigoplopes.mandinha_api.dtos.ProductResponseDTO;
import com.rodrigoplopes.mandinha_api.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(
            @RequestBody @Valid ProductRequestDTO dto,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();
        return ResponseEntity.ok(service.create(dto, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable String id,
            @RequestBody @Valid ProductRequestDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 🔧 controle de estoque

    @PatchMapping("/{id}/add")
    public ResponseEntity<Void> addQuantity(
            @PathVariable String id,
            @RequestParam int quantity
    ) {
        service.addQuantity(id, quantity);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/remove")
    public ResponseEntity<Void> removeQuantity(
            @PathVariable String id,
            @RequestParam int quantity
    ) {
        service.removeQuantity(id, quantity);
        return ResponseEntity.ok().build();
    }
}
