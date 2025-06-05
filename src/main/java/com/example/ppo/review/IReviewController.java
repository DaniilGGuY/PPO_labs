package com.example.ppo.review;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface IReviewController {
    ResponseEntity<?> createReview(@RequestBody Review review);

    ResponseEntity<?> updateReview(@RequestBody Review review);

    ResponseEntity<?> getClientReviews(@PathVariable String clientLogin);

    ResponseEntity<?> getConsultantReviews(@PathVariable String consultantLogin);

    ResponseEntity<?> getConsultantAverageRating(@PathVariable String consultantLogin);
}