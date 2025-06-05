package com.example.ppo.exception;

public class ValidationException extends BusinessException {
    public ValidationException(String field, String msg, String code) {
        super(String.format("Validation error for %s: %s", field, msg), code);
    }

    public ValidationException(String msg, String code) {
        super(String.format("Validation error: %s", msg), code);
    }
}