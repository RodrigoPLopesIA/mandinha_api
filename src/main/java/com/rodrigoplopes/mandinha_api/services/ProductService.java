package com.rodrigoplopes.mandinha_api.services;

import com.rodrigoplopes.mandinha_api.dtos.ProductResponseDTO;
import com.rodrigoplopes.mandinha_api.dtos.ProductRequestDTO;
import com.rodrigoplopes.mandinha_api.entities.Product;
import com.rodrigoplopes.mandinha_api.exceptions.InsufficientStockException;
import com.rodrigoplopes.mandinha_api.exceptions.ProductNotFoundException;
import com.rodrigoplopes.mandinha_api.mappers.ProductMapper;
import com.rodrigoplopes.mandinha_api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    
    public ProductResponseDTO create(ProductRequestDTO dto, String userId) {
        Product product = mapper.toEntity(dto);
        product.setProfileId(userId);
        return mapper.toDTO(repository.save(product));
    }

    
    public ProductResponseDTO update(String id, ProductRequestDTO dto) {
        Product product = findEntityById(id);
        mapper.updateEntityFromDTO(dto, product);
        return mapper.toDTO(repository.save(product));
    }

    
    public void delete(String id) {
        repository.deleteById(id);
    }

    
    public ProductResponseDTO findById(String id) {
        return mapper.toDTO(findEntityById(id));
    }

    
    public List<ProductResponseDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
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
}