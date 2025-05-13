package com.example.ppo.review;

import com.example.ppo.client.Client;
import com.example.ppo.consultant.Consultant;
import com.example.ppo.exception.BusinessException;
import com.example.ppo.orderproduct.OrderProduct;
import java.util.List;

public interface IReviewService {
    Review createReview(Client client, Consultant consultant, int rating, String text) throws BusinessException;
    double getAverageRating(Consultant consultant);
    List<Review> getConsultantReviews(Consultant consultant);
}