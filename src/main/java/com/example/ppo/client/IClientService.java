package com.example.ppo.client;

import com.example.ppo.exception.client.*;
import com.example.ppo.message.Message;
import com.example.ppo.order.Order;
import com.example.ppo.review.Review;

import java.util.List;

public interface IClientService {
    Client registerClient(Client client) 
        throws ClientValidationException, ClientAlreadyExistsException, ClientOperationException;
    Client getClientByLogin(String login) 
        throws ClientNotFoundException, ClientOperationException;
    Client updateClient(Client client) 
        throws ClientNotFoundException, ClientValidationException, ClientOperationException;
    void deleteClient(String login) 
        throws ClientNotFoundException, ClientOperationException;
    boolean clientExists(String login)
        throws ClientOperationException;
    List<Order> getClientOrders(Client client);
    List<Message> getClientMessages(Client client);
    List<Review> getClientReviews(Client client);
}