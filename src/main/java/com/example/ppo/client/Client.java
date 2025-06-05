package com.example.ppo.client;

import com.example.ppo.user.User;
import com.example.ppo.order.Order;
import com.example.ppo.review.Review;
import com.example.ppo.message.Message;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;
import java.util.ArrayList;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "client")
@Document(collection = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
@Schema(description = "Client entity")
public class Client extends User {
    
    @Column
    @Field("company")
    private String company;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @DBRef(lazy = true)
    @Field("orders")
    @Builder.Default
    private List<Order> orders = new ArrayList<>();
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @DBRef(lazy = true)
    @Field("messages")
    @Builder.Default
    private List<Message> messages = new ArrayList<>();
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @DBRef(lazy = true)
    @Field("reviews")
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();
    
    public void addOrder(Order order) {
        orders.add(order);
        order.setClient(this);
    }
    
    public void removeOrder(Order order) {
        orders.remove(order);
        order.setClient(null);
    }
    
    public void addMessage(Message message) {
        messages.add(message);
        message.setClient(this);
    }
    
    public void removeMessage(Message message) {
        messages.remove(message);
        message.setClient(null);
    }
    
    public void addReview(Review review) {
        reviews.add(review);
        review.setClient(this);
    }
    
    public void removeReview(Review review) {
        reviews.remove(review);
        review.setClient(null);
    }

    @Override
    public boolean isValid() {
        return super.isValid() && company != null && !company.isBlank();
    }
}