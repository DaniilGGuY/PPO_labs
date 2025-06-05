package com.example.ppo.exception;

public class BusinessException extends RuntimeException {
    private final String code;

    public BusinessException(String msg, String code) {
        super(msg);
        this.code = code;
    }

    public BusinessException(String code) {
        super("Unexpected error");
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}