package com.rodrigoplopes.mandinha_api.services;

import com.rodrigoplopes.mandinha_api.dtos.ProductResponseDTO;
import com.rodrigoplopes.mandinha_api.dtos.ProductRequestDTO;
import com.rodrigoplopes.mandinha_api.entities.Product;
import com.rodrigoplopes.mandinha_api.exceptions.InsufficientStockException;
import com.rodrigoplopes.mandinha_api.exceptions.ProductAlreadyExistsException;
import com.rodrigoplopes.mandinha_api.exceptions.ProductNotFoundException;
import com.rodrigoplopes.mandinha_api.mappers.ProductMapper;
import com.rodrigoplopes.mandinha_api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;


    public ProductResponseDTO create(ProductRequestDTO dto, String userId) {
        Product product = mapper.toEntity(dto);
        product.setProfileId(userId);

        if (existsByName(dto.name()))
            throw new ProductAlreadyExistsException(dto.name());

        return mapper.toDTO(repository.save(product));
    }


    public ProductResponseDTO update(String id, ProductRequestDTO dto) {
        Product product = findEntityById(id);

        isProductAlreadyStored(product, dto);

        mapper.updateEntityFromDTO(dto, product);
        return mapper.toDTO(repository.save(product));
    }


    public void delete(String id) {
        repository.deleteById(id);
    }


    public ProductResponseDTO findById(String id) {
        return mapper.toDTO(findEntityById(id));
    }



    public List<ProductResponseDTO> findAllFiltered(String name, String startDate, String endDate) {

        LocalDateTime start = null;
        LocalDateTime end = null;

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

        if (startDate != null && !startDate.isEmpty()) {
            start = LocalDate.parse(startDate, formatter).atStartOfDay();
        }

        if (endDate != null && !endDate.isEmpty()) {
            end = LocalDate.parse(endDate, formatter).atTime(23, 59, 59);
        }
        final LocalDateTime finalStart = start;
        final LocalDateTime finalEnd = end;

        List<Product> products = repository.findAll().stream()
                .filter(p -> name == null || p.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(p -> finalStart == null || !p.getCreatedAt().isBefore(finalStart))
                .filter(p -> finalEnd == null || !p.getCreatedAt().isAfter(finalEnd))
                .toList();

        return products.stream().map(mapper::toDTO).toList();
    }


    public void addQuantity(String productId, int quantity) {
        Product product = findEntityById(productId);
        product.setQuantity(product.getQuantity() + quantity);
        repository.save(product);
    }


    public void removeQuantity(String productId, int quantity) {
        Product product = findEntityById(productId);

        if (product.getQuantity() < quantity) {
            throw new InsufficientStockException(product.getName());
        }

        product.setQuantity(product.getQuantity() - quantity);
        repository.save(product);
    }

    private Product findEntityById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    private boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    private void isProductAlreadyStored(Product product, ProductRequestDTO dto) {
        if (!product.getName().equals(dto.name()) && existsByName(dto.name())) {
            throw new ProductAlreadyExistsException("Já existe um produto com o nome '" + dto.name() + "'");
        }
    }
}