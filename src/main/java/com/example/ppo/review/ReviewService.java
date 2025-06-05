package com.example.ppo.review;

import com.example.ppo.client.Client;
import com.example.ppo.consultant.Consultant;
import com.example.ppo.consultant.IConsultantRepository;
import com.example.ppo.exception.order.OrderOperationException;
import com.example.ppo.exception.review.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Service
@Transactional
@Slf4j
public class ReviewService implements IReviewService {
    private final IReviewRepository reviewRepo;
    private final IConsultantRepository consRepo;

    public ReviewService(IReviewRepository reviewRepo, IConsultantRepository consRepo) {
        this.reviewRepo = reviewRepo;
        this.consRepo = consRepo;
    }

    @Override
    public Review createReview(Review review) 
            throws ReviewValidationException, ReviewOperationException {
        if (!review.isValid()) {
            log.warn("Invalid review");
            throw new ReviewValidationException("Invalid review data");
        }

        try {
            review = reviewRepo.save(review);
            review.getClient().addReview(review);
            log.info("Client {} post review {}", review.getClient().getLogin(), review.getId());
            review.getConsultant().addReview(review);
            log.info("Consultant {} get review {}", review.getConsultant().getLogin(), review.getId());
            Consultant cons = consRepo.findByLogin(review.getConsultant().getLogin()).get();
            cons.setRating(getConsultantAverageRating(cons));
            log.info("Changed rating {} to {}", cons.getFio(), cons.getRating());
            consRepo.update(cons);
            return review;
        } catch (Exception e) {
            log.error("Failed to save review", e);
            throw new ReviewOperationException("Failed to create review: " + e.getMessage());
        }
    }

    @Override
    public Review updateReview(Review review) 
            throws ReviewNotFoundException, ReviewValidationException, ReviewOperationException {
        if (!review.isValid()) {
            log.warn("Invalid review");
            throw new ReviewValidationException("Invalid review data");
        }

        try {
            review = reviewRepo.update(review);
            log.info("Updated review with id={}", review.getId());
            return review;
        } catch (Exception e) {
            log.error("Failed to update review", e);
            throw new ReviewOperationException("Failed to update review: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Review> getClientReviews(Client client) 
            throws ReviewOperationException {
        try {
            return reviewRepo.findByClient_IdOrderByDatetimeDesc(client.getId());
        } catch (Exception e) {
            log.error("Failed to get review", e);
            throw new ReviewOperationException("Failed to get review: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Review> getConsultantReviews(Consultant consultant) 
            throws ReviewOperationException {
        try {
            return reviewRepo.findByConsultant_IdOrderByDatetimeDesc(consultant.getId());
        } catch (Exception e) {
            log.error("Failed to get review", e);
            throw new ReviewOperationException("Failed to get review: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public double getConsultantAverageRating(Consultant consultant) 
            throws ReviewOperationException {
        try {
            List<Review> reviews = reviewRepo.findByConsultant_IdOrderByDatetimeDesc(consultant.getId());
            if (reviews.isEmpty()) {
                return 0.0;
            }
            double avgRating = reviews.stream().mapToDouble(Review::getNumericRating).average().orElse(0.0);
            consultant.setRating(avgRating);
            log.info("Calculated {} average rating. It is {}", consultant.getFio(), avgRating);
            return avgRating;
        } catch (Exception e) {
            log.error("Failed to get review", e);
            throw new ReviewOperationException("Failed to get review: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Review> getAllReviews()
            throws OrderOperationException {
        try {
            return reviewRepo.findAll();
        } catch (Exception e) {
            log.error("Failed to get review", e);
            throw new ReviewOperationException("Failed to get review: " + e.getMessage());
        }
    }
}