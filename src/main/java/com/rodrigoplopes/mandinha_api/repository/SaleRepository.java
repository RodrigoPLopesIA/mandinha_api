package com.rodrigoplopes.mandinha_api.repository;

import com.rodrigoplopes.mandinha_api.entities.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface SaleRepository extends JpaRepository<Sale, String> {

    Page<Sale> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Sale> findByItemsProductNameContainingIgnoreCase(String name, Pageable pageable);
}
