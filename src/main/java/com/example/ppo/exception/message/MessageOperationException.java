package com.example.ppo.exception.message;

public class MessageOperationException extends MessageException {
    public MessageOperationException(String msg) {
        super(msg, "MESSAGE_OPERATION_ERROR");
    }
}
