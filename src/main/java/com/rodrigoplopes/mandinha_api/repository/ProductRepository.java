package com.rodrigoplopes.mandinha_api.repository;

import com.rodrigoplopes.mandinha_api.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {


    boolean existsByName(String name);

}
