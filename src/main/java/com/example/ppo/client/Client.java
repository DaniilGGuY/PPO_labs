package com.example.ppo.client;

import com.example.ppo.user.User;
import com.example.ppo.order.Order;
import com.example.ppo.review.Review;
import com.example.ppo.message.Message;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Client extends User {  
    @Column
    private String company;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
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
}