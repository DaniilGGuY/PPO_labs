package com.example.ppo.exception;

public class BusinessException extends Exception {
    private final String code;

    public BusinessException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}