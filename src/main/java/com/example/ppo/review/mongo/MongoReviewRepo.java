package com.example.ppo.review.mongo;

import com.example.ppo.exception.review.*;
import com.example.ppo.review.Review;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MongoReviewRepo extends MongoRepository<Review, Long> {

    @Override
    <S extends Review> S save(S review) throws ReviewOperationException;

    @Override
    Optional<Review> findById(Long id);

    @Override
    List<Review> findAll();

    List<Review> findByClient_IdOrderByDatetimeDesc(Long clientId);

    List<Review> findByConsultant_IdOrderByDatetimeDesc(Long consultantId);

    default Review update(Review review) {
        return save(review);
    }
}