package com.example.ppo.review;

import com.example.ppo.client.Client;
import com.example.ppo.consultant.Consultant;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

enum Rating {
    AWFUL(1),
    BAD(2),
    NORMAL(3),
    GOOD(4),
    AWESOME(5);

    private final int numericValue;

    Rating(int numericValue) {
        this.numericValue = numericValue;
    }

    public int getNumericValue() {
        return numericValue;
    }

    public static Rating fromValue(int value) {
        for (Rating r : values()) {
            if (r.numericValue == value) {
                return r;
            }
        }
        throw new IllegalArgumentException("Invalid rating value: " + value);
    }
}

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
	private Rating rating;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime datetime = LocalDateTime.now();
	
	@Column(columnDefinition = "TEXT")
	private String text;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id", nullable = false)
    private Consultant consultant;
	
	public void setRating(int ratingValue) {
	    this.rating = Rating.fromValue(ratingValue);
	}
	
	@PrePersist
	public void prePersist() {
		if (this.datetime == null) {
			this.datetime = LocalDateTime.now();
		}
	}
	
	public int getNumericRating() {
        return rating != null ? rating.getNumericValue() : 0;
    }
	
	public boolean isValid() {
        return rating != null;
    }
}