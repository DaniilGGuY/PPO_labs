package com.example.ppo.exception.orderproduct;

import com.example.ppo.exception.*;

public class OrderProductValidationException extends ValidationException {
    public OrderProductValidationException(String field, String msg) {
        super(field, msg, "ORDER_PRODUCT_VALIDATION_ERROR");
    }

    public OrderProductValidationException(String msg) {
        super(msg, "ORDER_PRODUCT_VALIDATION_ERROR");
    }
}
