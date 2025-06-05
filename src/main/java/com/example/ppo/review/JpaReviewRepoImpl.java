package com.example.ppo.review;

import com.example.ppo.review.jpa.JpaReviewRepo;
import java.util.List;
import java.util.Optional;

public class JpaReviewRepoImpl implements IReviewRepository {
    private final JpaReviewRepo repo;

    public JpaReviewRepoImpl(JpaReviewRepo repo) {
        this.repo = repo;
    }

    public Review save(Review review) {
        return repo.save(review);
    }

    public Optional<Review> findById(Long id) {
        return repo.findById(id);
    }

    public List<Review> findAll() {
        return repo.findAll();
    }

    public List<Review> findByClient_IdOrderByDatetimeDesc(Long clientId) {
        return repo.findByClient_IdOrderByDatetimeDesc(clientId);
    }

    public List<Review> findByConsultant_IdOrderByDatetimeDesc(Long consultantId) {
        return repo.findByConsultant_IdOrderByDatetimeDesc(consultantId);
    }

    public Review update(Review review) {
        return repo.update(review);
    }
}