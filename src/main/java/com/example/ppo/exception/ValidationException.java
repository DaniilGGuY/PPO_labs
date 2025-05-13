package com.example.ppo.exception;

import com.example.ppo.exception.BusinessException;

public class ValidationException extends BusinessException {
    public ValidationException(String field, String message, String code) {
        super(String.format("Validation error for %s: %s", field, message), code);
    }
}