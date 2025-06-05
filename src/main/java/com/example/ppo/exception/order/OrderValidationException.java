package com.example.ppo.exception.order;

import com.example.ppo.exception.*;

public class OrderValidationException extends ValidationException {
    public OrderValidationException(String field, String msg) {
        super(field, msg, "ORDER_VALIDATION_ERROR");
    }

    public OrderValidationException(String msg) {
        super(msg, "ORDER_VALIDATION_ERROR");
    }
}