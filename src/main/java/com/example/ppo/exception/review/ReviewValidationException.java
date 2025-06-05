package com.example.ppo.exception.review;

import com.example.ppo.exception.*;

public class ReviewValidationException extends ValidationException {
    public ReviewValidationException(String field, String msg) {
        super(field, msg, "REVIEW_VALIDATION_ERROR");
    }

    public ReviewValidationException(String msg) {
        super(msg, "REVIEW_VALIDATION_ERROR");
    }
}