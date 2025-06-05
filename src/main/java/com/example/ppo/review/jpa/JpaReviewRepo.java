package com.example.ppo.review.jpa;

import com.example.ppo.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaReviewRepo extends JpaRepository<Review, Long> {

    @Override
    @Transactional
    <S extends Review> S save(S review);

    @Override
    @Transactional(readOnly = true)
    Optional<Review> findById(Long id);

    @Override
    @Transactional(readOnly = true)
    List<Review> findAll();

    @Transactional(readOnly = true)
    List<Review> findByClient_IdOrderByDatetimeDesc(Long clientId);

    @Transactional(readOnly = true)
    List<Review> findByConsultant_IdOrderByDatetimeDesc(Long consultantId);

    @Transactional
    default Review update(Review review) {
        return save(review);
    }
}