package com.example.ppo.consultant;

import java.util.ArrayList;
import java.util.List;

import com.example.ppo.message.Message;
import com.example.ppo.order.Order;
import com.example.ppo.review.Review;
import com.example.ppo.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "consultants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Consultant extends User {
	@Column
    private String specialization;
	
    @Column
    private int age;
    
    @Column
    private int experience;
    
    @Column
    private double rating;
    
    @OneToMany(mappedBy = "consultant", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
    
    @OneToMany(mappedBy = "consultant", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();
    
    @OneToMany(mappedBy = "consultant", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();
    
    public void addOrder(Order order) {
    	orders.add(order);
    	order.setConsultant(this);
    }
    
    public void removeOrder(Order order) {
        orders.remove(order);
        order.setConsultant(null);
    }
    
    public void addMessage(Message message) {
    	messages.add(message);
    	message.setConsultant(this);
    }
    
    public void removeMessage(Message message) {
    	messages.remove(message);
    	message.setConsultant(null);
    }
    
    public void addReview(Review review) {
    	reviews.add(review);
    	review.setConsultant(this);
    }
    
    public void removeReview(Review review) {
    	reviews.remove(review);
    	review.setConsultant(null);
    }
    
    public double getAverageRating() {
    	if (reviews == null || reviews.isEmpty()) {
    		return 0.0;
    	}
    	double sum = 0.0;
    	for (Review rev : reviews) {
    	    sum += rev.getNumericRating();
    	}
    	return sum / reviews.size();
    }
}