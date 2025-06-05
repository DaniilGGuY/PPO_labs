package com.example.ppo.exception.message;

import com.example.ppo.exception.*;

public class MessageValidationException extends ValidationException {
    public MessageValidationException(String field, String msg) {
        super(field, msg, "MESSAGE_VALIDATION_ERROR");
    }
    
    public MessageValidationException(String msg) {
        super(msg, "MESSAGE_VALIDATION_ERROR");
    }
}