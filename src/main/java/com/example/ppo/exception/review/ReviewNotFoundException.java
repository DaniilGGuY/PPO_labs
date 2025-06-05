package com.example.ppo.exception.review;

public class ReviewNotFoundException extends ReviewException {
    public ReviewNotFoundException(Long reviewId) {
        super(String.format("Review with id %d not found", reviewId), "REVIEW_NOT_FOUND");
    }
}
