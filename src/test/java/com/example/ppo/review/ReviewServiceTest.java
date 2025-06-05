package com.example.ppo.review;

import com.example.ppo.client.Client;
import com.example.ppo.consultant.Consultant;
import com.example.ppo.consultant.IConsultantRepository;
import com.example.ppo.exception.review.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private IReviewRepository reviewRepo;

    @Mock
    private IConsultantRepository consRepo;

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private Client testClient;

    @Mock
    private Consultant testConsultant;

    @Mock
    private Review testReview;

    @BeforeEach
    void setUp() {
        testClient = Client.builder()
                .id(1L)
                .login("client1")
                .build();

        testConsultant = Consultant.builder()
                .id(1L)
                .login("consultant1")
                .build();

        testReview = Review.builder()
                .id(1L)
                .client(testClient)
                .consultant(testConsultant)
                .rating(Rating.AWESOME)
                .text("Great service!")
                .datetime(LocalDateTime.now())
                .build();
    }

    @Test
    void createReview_InvalidReview_ThrowsValidationException() {
        // Arrange
        Review invalidReview = Review.builder().build();

        // Act & Assert
        assertThrows(ReviewValidationException.class, () -> reviewService.createReview(invalidReview));
        verify(reviewRepo, never()).save(any());
    }

    @Test
    void createReview_RepositoryException_ThrowsOperationException() {
        // Arrange
        when(reviewRepo.save(any(Review.class))).thenThrow(new RuntimeException("DB error"));

        // Act & Assert
        assertThrows(ReviewOperationException.class, () -> reviewService.createReview(testReview));
    }

    @Test
    void updateReview_ValidReview_ReturnsUpdatedReview() throws ReviewValidationException, ReviewNotFoundException, ReviewOperationException {
        // Arrange
        when(reviewRepo.update(any(Review.class))).thenReturn(testReview);

        // Act
        Review result = reviewService.updateReview(testReview);

        // Assert
        assertNotNull(result);
        assertEquals(testReview, result);
        verify(reviewRepo).update(testReview);
    }

    @Test
    void updateReview_InvalidReview_ThrowsValidationException() {
        // Arrange
        Review invalidReview = Review.builder().build();

        // Act & Assert
        assertThrows(ReviewValidationException.class, () -> reviewService.updateReview(invalidReview));
        verify(reviewRepo, never()).update(any());
    }

    @Test
    void getClientReviews_ReturnsOrderedReviews() {
        // Arrange
        List<Review> expectedReviews = List.of(testReview);
        when(reviewRepo.findByClient_IdOrderByDatetimeDesc(1L))
                .thenReturn(expectedReviews);

        // Act
        List<Review> result = reviewService.getClientReviews(testClient);

        // Assert
        assertEquals(1, result.size());
        assertEquals(expectedReviews, result);
        verify(reviewRepo).findByClient_IdOrderByDatetimeDesc(1L);
    }

    @Test
    void getConsultantReviews_ReturnsOrderedReviews() {
        // Arrange
        List<Review> expectedReviews = List.of(testReview);
        when(reviewRepo.findByConsultant_IdOrderByDatetimeDesc(1L))
                .thenReturn(expectedReviews);

        // Act
        List<Review> result = reviewService.getConsultantReviews(testConsultant);

        // Assert
        assertEquals(1, result.size());
        assertEquals(expectedReviews, result);
        verify(reviewRepo).findByConsultant_IdOrderByDatetimeDesc(1L);
    }

    @Test
    void getConsultantAverageRating_WithReviews_ReturnsCorrectAverage() {
        // Arrange
        Review review1 = Review.builder().rating(Rating.GOOD).build(); // 4
        Review review2 = Review.builder().rating(Rating.AWESOME).build();   // 5
        when(reviewRepo.findByConsultant_IdOrderByDatetimeDesc(1L))
                .thenReturn(List.of(review1, review2));

        // Act
        double average = reviewService.getConsultantAverageRating(testConsultant);

        // Assert
        assertEquals(4.5, average);
    }

    @Test
    void getConsultantAverageRating_NoReviews_ReturnsZero() {
        // Arrange
        when(reviewRepo.findByConsultant_IdOrderByDatetimeDesc(1L))
                .thenReturn(List.of());

        // Act
        double average = reviewService.getConsultantAverageRating(testConsultant);

        // Assert
        assertEquals(0.0, average);
    }
}