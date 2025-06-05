package com.example.ppo.exception.consultant;

import com.example.ppo.exception.*;

public class ConsultantValidationException extends ValidationException {
    public ConsultantValidationException(String field, String msg) {
        super(field, msg, "CONSULTANT_VALIDATION_ERROR");
    }

    public ConsultantValidationException(String msg) {
        super(msg, "CONSULTANT_VALIDATION_ERROR");
    }
}