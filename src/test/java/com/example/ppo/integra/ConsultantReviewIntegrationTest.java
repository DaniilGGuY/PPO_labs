package com.example.ppo.integra;

import com.example.ppo.client.Client;
import com.example.ppo.client.ClientService;
import com.example.ppo.consultant.Consultant;
import com.example.ppo.consultant.ConsultantService;
import com.example.ppo.review.Rating;
import com.example.ppo.review.Review;
import com.example.ppo.review.ReviewService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ConsultantReviewIntegrationTest {
    @Autowired
    private ConsultantService consultantService;
    
    @Autowired
    private ClientService clientService;
    
    @Autowired
    private ReviewService reviewService;

    @Test
    void shouldCalculateAverageRatingForConsultant() throws Exception {
        Consultant consultant = consultantService.registerConsultant(Consultant.builder()
            .login("consultant1")
            .password("password123")
            .tel("+71234567890")
            .email("consultant@test.com")
            .fio("Alex Alex Alex")
            .specialization("accountment")
            .age(30)
            .experience(8)
            .build());

        Client client1 = clientService.registerClient(Client.builder()
            .login("test_client_1")
            .password("password123")
            .tel("+71234567890")
            .email("test" + System.currentTimeMillis() + "@example.com")
            .company("Test Company")
            .build());

        Client client2 = clientService.registerClient(Client.builder()
            .login("test_client_2")
            .password("password1234")
            .tel("+71234567891")
            .email("test" + System.currentTimeMillis() + "@example.com")
            .company("Test Company")
            .build());

        Review review1 = Review.builder()
            .rating(Rating.GOOD)
            .client(client1)
            .consultant(consultant)
            .build();
        reviewService.createReview(review1);

        Review review2 = Review.builder()
            .rating(Rating.AWESOME)
            .client(client2)
            .consultant(consultant)
            .build();
        reviewService.createReview(review2);
        assertEquals(4.5, consultantService.getConsultantByLogin(consultant.getLogin()).getRating());  
    }
}