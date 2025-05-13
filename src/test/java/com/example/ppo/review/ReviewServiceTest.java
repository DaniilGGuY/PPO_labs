package com.example.ppo.review;

import com.example.ppo.client.Client;
import com.example.ppo.consultant.Consultant;
import com.example.ppo.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    
    @InjectMocks
    private ReviewService reviewService;
    
    @Mock(lenient = true)
    private Client mockClient;
    
    @Mock(lenient = true)
    private Consultant mockConsultant;
    
    @BeforeEach
    void setUp() {
        when(mockClient.getId()).thenReturn(1L);
        when(mockConsultant.getId()).thenReturn(1L);
    }

    @Test
    void createReview_ShouldSuccessfullyCreateReview() throws BusinessException {
        Review review = reviewService.createReview(mockClient, mockConsultant, 5, "Excellent service!");
        
        assertNotNull(review);
        assertEquals(5, review.getNumericRating());
        assertEquals("Excellent service!", review.getText());
        assertEquals(mockClient, review.getClient());
        assertEquals(mockConsultant, review.getConsultant());
        assertNotNull(review.getDatetime());
        assertTrue(review.getId() > 0);
    }

    @Test
    void createReview_ShouldSetCurrentDateTime() throws BusinessException {
        Review review = reviewService.createReview(mockClient, mockConsultant, 4, "Good");
        LocalDateTime now = LocalDateTime.now();
        
        assertTrue(review.getDatetime().isBefore(now.plusSeconds(1)));
        assertTrue(review.getDatetime().isAfter(now.minusSeconds(1)));
    }

    @Test
    void createReview_WithInvalidRating_ShouldThrowException() {
        assertThrows(BusinessException.class, () -> reviewService.createReview(mockClient, mockConsultant, 0, "Test"));
        assertThrows(BusinessException.class, () -> reviewService.createReview(mockClient, mockConsultant, 6, "Test"));
    }

    @Test
    void createReview_WithEmptyText_ShouldThrowException() {
        assertThrows(BusinessException.class, () -> reviewService.createReview(mockClient, mockConsultant, 3, ""));
        assertThrows(BusinessException.class, () -> reviewService.createReview(mockClient, mockConsultant, 3, "   "));
    }

    @Test
    void createReview_WithNullText_ShouldThrowException() {
        assertThrows(BusinessException.class, () -> reviewService.createReview(mockClient, mockConsultant, 3, null));
    }

    @Test
    void getAverageRating_ShouldReturnCorrectValue() throws BusinessException {
        Review review1 = new Review();
        review1.setRating(5);
        review1.setConsultant(mockConsultant);
        
        Review review2 = new Review();
        review2.setRating(3);
        review2.setConsultant(mockConsultant);
        
        Review review3 = new Review();
        review3.setRating(4);
        review3.setConsultant(mockConsultant);
        
        reviewService.createReview(mockClient, mockConsultant, 5, "Great");
        reviewService.createReview(mockClient, mockConsultant, 3, "Average");
        reviewService.createReview(mockClient, mockConsultant, 4, "Good");
        
        double average = reviewService.getAverageRating(mockConsultant);
        
        assertEquals(4.0, average, 0.001);
    }

    @Test
    void getAverageRating_WithNoReviews_ShouldReturnZero() {
        double average = reviewService.getAverageRating(mockConsultant);
        assertEquals(0.0, average, 0.001);
    }

    @Test
    void createMultipleReviews_ShouldGenerateUniqueIds() throws BusinessException {
        Review review1 = reviewService.createReview(mockClient, mockConsultant, 5, "First");
        Review review2 = reviewService.createReview(mockClient, mockConsultant, 4, "Second");
        Review review3 = reviewService.createReview(mockClient, mockConsultant, 3, "Third");
        
        assertNotEquals(review1.getId(), review2.getId());
        assertNotEquals(review1.getId(), review3.getId());
        assertNotEquals(review2.getId(), review3.getId());
    }
}