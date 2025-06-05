package com.example.ppo.review;

import java.util.List;
import java.util.Optional;

public interface IReviewRepository {
    Review save(Review review);
    Optional<Review> findById(Long id);
    List<Review> findAll();
    List<Review> findByClient_IdOrderByDatetimeDesc(Long clientId);
    List<Review> findByConsultant_IdOrderByDatetimeDesc(Long consultantId);
    Review update(Review review);
}