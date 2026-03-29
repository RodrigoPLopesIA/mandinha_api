package com.rodrigoplopes.mandinha_api.exceptions;

public class InsufficientStockException extends BusinessException {

    public InsufficientStockException(String productName) {
        super("Insufficient stock for product: " + productName);
    }
}
