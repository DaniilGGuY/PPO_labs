package com.example.ppo.review;

import com.example.ppo.client.Client;
import com.example.ppo.consultant.Consultant;
import com.example.ppo.exception.order.OrderOperationException;
import com.example.ppo.exception.review.*;
import java.util.List;

public interface IReviewService {
    Review createReview(Review review) 
        throws ReviewValidationException, ReviewOperationException;
    Review updateReview(Review review) 
        throws ReviewNotFoundException, ReviewValidationException, ReviewOperationException;   
    List<Review> getClientReviews(Client client)
        throws OrderOperationException;
    List<Review> getConsultantReviews(Consultant consultant)
        throws OrderOperationException;
    double getConsultantAverageRating(Consultant consultant)
        throws OrderOperationException;
    List<Review> getAllReviews()
        throws OrderOperationException;
}