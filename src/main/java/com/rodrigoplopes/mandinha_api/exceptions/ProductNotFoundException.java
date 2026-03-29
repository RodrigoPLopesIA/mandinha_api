package com.rodrigoplopes.mandinha_api.exceptions;

public class ProductNotFoundException extends BusinessException {

    public ProductNotFoundException(String id) {
        super("Product not found with id: " + id);
    }
}