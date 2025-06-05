package com.example.ppo.exception.product;

public class ProductNotFoundException extends ProductException {
    public ProductNotFoundException(Long productId) {
        super(String.format("Product with id %d not found", productId), "PRODUCT_NOT_FOUND");
    }
}