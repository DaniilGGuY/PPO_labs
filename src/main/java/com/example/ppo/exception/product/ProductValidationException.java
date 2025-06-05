package com.example.ppo.exception.product;

import com.example.ppo.exception.*;

public class ProductValidationException extends ValidationException {
    public ProductValidationException(String field, String msg) {
        super(field, msg, "PRODUCT_VALIDATION_ERROR");
    }

    public ProductValidationException(String msg) {
        super(msg, "PRODUCT_VALIDATION_ERROR");
    }
}