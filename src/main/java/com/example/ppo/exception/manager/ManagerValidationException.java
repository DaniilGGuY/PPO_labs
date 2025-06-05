package com.example.ppo.exception.manager;

import com.example.ppo.exception.*;

public class ManagerValidationException extends ValidationException {
    public ManagerValidationException(String field, String msg) {
        super(field, msg, "MANAGER_VALIDATION_ERROR");
    }

    public ManagerValidationException(String msg) {
        super(msg, "MANAGER_VALIDATION_ERROR");
    }
}