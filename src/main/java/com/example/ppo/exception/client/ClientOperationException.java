package com.example.ppo.exception.client;

public class ClientOperationException extends ClientException {
    public ClientOperationException(String msg) {
        super(msg, "CLIENT_OPERATION_ERROR");
    }
}