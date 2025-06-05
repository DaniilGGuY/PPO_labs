package com.example.ppo.exception.client;

import com.example.ppo.exception.*;

public class ClientValidationException extends ValidationException {
    public ClientValidationException(String field, String msg) {
        super(field, msg, "CLIENT_VALIDATION_ERROR");
    }

    public ClientValidationException(String msg) {
        super(msg, "CLIENT_VALIDATION_ERROR");
    }
}
