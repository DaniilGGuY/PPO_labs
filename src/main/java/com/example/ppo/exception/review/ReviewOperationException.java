package com.example.ppo.exception.review;

public class ReviewOperationException extends ReviewException {
    public ReviewOperationException(String msg) {
        super(msg, "REVIEW_OPERATION_ERROR");
    }
}
