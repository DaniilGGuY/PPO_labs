package com.example.ppo.exception.product;

public class ProductOperationException extends ProductException {
    public ProductOperationException(String msg) {
        super(msg, "PRODUCT_OPERATION_ERROR");
    }
}
