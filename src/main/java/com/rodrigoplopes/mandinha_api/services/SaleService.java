package com.rodrigoplopes.mandinha_api.services;

import com.rodrigoplopes.mandinha_api.dtos.SaleItemResponseDTO;
import com.rodrigoplopes.mandinha_api.dtos.SaleRequestDTO;
import com.rodrigoplopes.mandinha_api.dtos.SaleResponseDTO;
import com.rodrigoplopes.mandinha_api.dtos.SaleItemRequestDTO;
import com.rodrigoplopes.mandinha_api.entities.*;
import com.rodrigoplopes.mandinha_api.exceptions.InsufficientStockException;
import com.rodrigoplopes.mandinha_api.exceptions.ProductNotFoundException;
import com.rodrigoplopes.mandinha_api.exceptions.SaleNotFoundException;
import com.rodrigoplopes.mandinha_api.mappers.SaleMapper;
import com.rodrigoplopes.mandinha_api.repository.ProductRepository;
import com.rodrigoplopes.mandinha_api.repository.SaleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final SaleMapper saleMapper;


    @Transactional
    public SaleResponseDTO create(SaleRequestDTO dto, String userId) {

        Sale sale = new Sale();
        sale.setProfileId(userId);

        List<SaleItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (SaleItemRequestDTO itemDTO : dto.items()) {

            Product product = findProduct(itemDTO.productId());

            validateStock(product, itemDTO.quantity());

            product.setQuantity(product.getQuantity() - itemDTO.quantity());

            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(itemDTO.quantity()));

            SaleItem item = SaleItem.builder()
                    .sale(sale)
                    .product(product)
                    .quantity(itemDTO.quantity())
                    .unitPrice(product.getPrice())
                    .subtotal(subtotal)
                    .build();

            items.add(item);
            total = total.add(subtotal);
        }

        sale.setItems(items);
        sale.setTotalAmount(total);

        Sale savedSale = saleRepository.save(sale);

        return saleMapper.toDTO(savedSale);
    }

    
    @Transactional
    public SaleResponseDTO update(String id, SaleRequestDTO dto) {

        Sale sale = findSale(id);

        for (SaleItem item : sale.getItems()) {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() + item.getQuantity());
        }

        sale.getItems().clear();

        return create(dto, sale.getProfileId());
    }

    
    @Transactional
    public void delete(String id) {

        Sale sale = findSale(id);

        // 🔁 devolve estoque
        for (SaleItem item : sale.getItems()) {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() + item.getQuantity());
        }

        saleRepository.delete(sale);
    }

    
    public Page<SaleResponseDTO> findAll(Pageable pageable) {
        return saleRepository.findAll(pageable).map(saleMapper::toDTO);
    }

    
    public Page<SaleResponseDTO> findByPeriod(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return saleRepository.findByCreatedAtBetween(start, end, pageable)
                .map(saleMapper::toDTO);
    }

    
    public Page<SaleResponseDTO> findByProductName(String name, Pageable pageable) {
        return saleRepository.findByItemsProductNameContainingIgnoreCase(name, pageable)
                .map(saleMapper::toDTO);
    }

    // =======================
    // PRIVATE METHODS
    // =======================

    private Product findProduct(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    private Sale findSale(String id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new SaleNotFoundException(id));
    }

    private void validateStock(Product product, int quantity) {
        if (product.getQuantity() < quantity) {
            throw new InsufficientStockException(product.getName());
        }
    }
}