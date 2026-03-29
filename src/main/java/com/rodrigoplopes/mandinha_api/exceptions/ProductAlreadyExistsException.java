package com.rodrigoplopes.mandinha_api.exceptions;

public class ProductAlreadyExistsException extends BusinessException{

    public ProductAlreadyExistsException(String message) {
        super("Product with name '" + message + "' already exists.");
    }
}
