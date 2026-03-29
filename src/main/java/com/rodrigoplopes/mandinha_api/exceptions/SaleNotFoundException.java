package com.rodrigoplopes.mandinha_api.exceptions;

public class SaleNotFoundException extends BusinessException {

    public SaleNotFoundException(String id) {
        super("Sale not found with id: " + id);
    }
}