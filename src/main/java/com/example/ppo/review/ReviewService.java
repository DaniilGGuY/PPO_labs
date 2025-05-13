package com.example.ppo.review;


import com.example.ppo.review.IReviewService;
import com.example.ppo.exception.BusinessException;
import com.example.ppo.client.Client;
import com.example.ppo.consultant.Consultant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.time.LocalDateTime;

public class ReviewService implements IReviewService {
    private final Map<Long, Review> reviews = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Review createReview(Client client, Consultant consultant, int ratingValue, String text) throws BusinessException {
        if (ratingValue < 1 || ratingValue > 5) {
            throw new BusinessException("Rating must be between 1 and 5", "INVALID_RATING");
        }
        if (text == null || text.trim().isEmpty()) {
            throw new BusinessException("Review cannot be empty", "EMPTY_TEXT");
        }

        Review review = new Review();
        review.setId(idGenerator.getAndIncrement());
        review.setRating(ratingValue);
        review.setText(text);
        review.setDatetime(LocalDateTime.now());
        review.setClient(client);
        review.setConsultant(consultant);

        reviews.put(review.getId(), review);
        return review;
    }

    @Override
    public double getAverageRating(Consultant consultant) {
        return getConsultantReviews(consultant).stream()
            .mapToInt(Review::getNumericRating)
            .average()
            .orElse(0.0);
    }

    @Override
    public List<Review> getConsultantReviews(Consultant consultant) {
        return reviews.values().stream()
            .filter(r -> r.getConsultant().equals(consultant))
            .toList();
    }
}