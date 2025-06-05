package com.example.ppo.review;

import com.example.ppo.client.Client;
import com.example.ppo.client.IClientService;
import com.example.ppo.consultant.Consultant;
import com.example.ppo.consultant.IConsultantService;
import com.example.ppo.exception.review.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Review Management", description = "APIs for managing reviews")
@Slf4j
public class ReviewController implements IReviewController {

    private final IReviewService reviewService;
    private final IClientService clientService;
    private final IConsultantService consultantService;

    public ReviewController(IReviewService reviewService, IClientService clientService, IConsultantService consultantService) {
        this.reviewService = reviewService;
        this.clientService = clientService;
        this.consultantService = consultantService;
    }

    @Operation(summary = "Create review", description = "Creates a new review for consultant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Review created", content = @Content(schema = @Schema(implementation = Review.class))),
        @ApiResponse(responseCode = "400", description = "Invalid review data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody Review review) {
        try {
            log.info("POST /api/reviewes");
            Review createdReview = reviewService.createReview(review);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
        } catch (ReviewValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ReviewOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Update review", description = "Updates an existing review")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Review updated", content = @Content(schema = @Schema(implementation = Review.class))),
        @ApiResponse(responseCode = "400", description = "Invalid review data"),
        @ApiResponse(responseCode = "404", description = "Review not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping
    public ResponseEntity<?> updateReview(@RequestBody Review review) {
        try {
            log.info("PUT /api/reviewes");
            Review updatedReview = reviewService.updateReview(review);
            return ResponseEntity.ok(updatedReview);
        } catch (ReviewValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ReviewNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ReviewOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Get client reviews", description = "Returns all reviews by client")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reviews found", content = @Content(schema = @Schema(implementation = Review[].class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/client/{clientLogin}")
    public ResponseEntity<?> getClientReviews(@PathVariable String clientLogin) {
        try {
            log.info("GET /api/reviewes/client/{}", clientLogin);
            Client client = clientService.getClientByLogin(clientLogin);
            List<Review> reviews = reviewService.getClientReviews(client);
            return ResponseEntity.ok(reviews);
        } catch (ReviewOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Get consultant reviews", description = "Returns all reviews for consultant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reviews found", content = @Content(schema = @Schema(implementation = Review[].class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })    
    @GetMapping("/consultant/{consultantLogin}")
    public ResponseEntity<?> getConsultantReviews(@PathVariable String consultantLogin) {
        try {
            log.info("GET /api/reviewes/consultant/{}", consultantLogin);
            Consultant consultant = consultantService.getConsultantByLogin(consultantLogin);
            List<Review> reviews = reviewService.getConsultantReviews(consultant);
            return ResponseEntity.ok(reviews);
        } catch (ReviewOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Get consultant rating", description = "Returns average rating for consultant")
    @ApiResponses(value = {    
        @ApiResponse(responseCode = "200", description = "Rating calculated", content = @Content(schema = @Schema(implementation = Double.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    }) 
    @GetMapping("/consultant/{consultantLogin}/rating")
    public ResponseEntity<?> getConsultantAverageRating(@PathVariable String consultantLogin) {
        try {
            log.info("GET /api/reviewes/consultant/{}/rating", consultantLogin);
            Consultant consultant = consultantService.getConsultantByLogin(consultantLogin);
            double rating = reviewService.getConsultantAverageRating(consultant);
            return ResponseEntity.ok(rating);
        } catch (ReviewOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}