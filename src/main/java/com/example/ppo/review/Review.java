package com.example.ppo.review;

import com.example.ppo.client.Client;
import com.example.ppo.consultant.Consultant;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "review")
@Document(collection = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Schema(description = "Review entity")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.annotation.Id
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Field("rating")
    private Rating rating;
    
    @Column
    @Field("datetime")
    @Builder.Default
    private LocalDateTime datetime = LocalDateTime.now();
    
    @Column(columnDefinition = "TEXT")
    @Field("text")
    @Builder.Default
    private String text = "";

    // Только JPA аннотации
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id")
    private Consultant consultant;
    
    // MongoDB-специфичные поля (транзиентные - не сохраняются в JPA)
    @Transient
    @Field("client_id")
    private String clientRef;
    
    @Transient
    @Field("consultant_if")
    private String consultantRef;
    
    @PostLoad
    private void postLoad() {
        if (client != null) {
            this.clientRef = client.getId().toString();
        }
        if (consultant != null) {
            this.consultantRef = consultant.getId().toString();
        }
    }
    
    public void setRating(int ratingValue) {
        this.rating = Rating.fromValue(ratingValue);
    }
    
    public int getNumericRating() {
        return rating != null ? rating.getNumericValue() : 0;
    }
    
    public boolean isValid() {
        return rating != null && 
               consultant != null && 
               client != null &&
               datetime != null && 
               text != null;
    }
}