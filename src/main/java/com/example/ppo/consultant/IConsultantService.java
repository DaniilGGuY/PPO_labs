package com.example.ppo.consultant;

import com.example.ppo.exception.consultant.*;
import com.example.ppo.message.Message;
import com.example.ppo.order.Order;
import com.example.ppo.review.Review;

import java.util.List;

public interface IConsultantService {
    Consultant registerConsultant(Consultant consultant) 
        throws ConsultantValidationException, ConsultantAlreadyExistsException, ConsultantOperationException;
    Consultant getConsultantByLogin(String login) 
        throws ConsultantNotFoundException, ConsultantOperationException;
    Consultant updateConsultant(Consultant consultant) 
        throws ConsultantNotFoundException, ConsultantValidationException, ConsultantOperationException;
    void deleteConsultant(String login) 
        throws ConsultantNotFoundException, ConsultantOperationException;
    boolean consultantExists(String login)
        throws ConsultantOperationException;
    List<Consultant> findAllConsultants()
        throws ConsultantOperationException;
    List<Order> getConsultantOrders(Consultant consultant);
    List<Message> getConsultantMessages(Consultant consultant);
    List<Review> getConsultantReviews(Consultant consultant);
}