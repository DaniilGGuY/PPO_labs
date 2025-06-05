package com.example.ppo.order;

import com.example.ppo.client.Client;
import com.example.ppo.consultant.Consultant;
import com.example.ppo.exception.order.*;
import com.example.ppo.orderproduct.ProductQuantityPair;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
	Order createOrder(Client client, List<ProductQuantityPair> items) 
        throws OrderValidationException, OrderOperationException;
    void addProductToOrder(Order order, ProductQuantityPair op)
        throws OrderValidationException, OrderOperationException;
    Order assignConsultant(Order order, Consultant consultant) 
        throws OrderNotFoundException, OrderOperationException;
    Order completeOrder(Order order, Consultant consultant) 
        throws OrderNotFoundException, OrderOperationException;
    Order cancelOrder(Order order, Consultant consultant) 
        throws OrderNotFoundException, OrderOperationException;
    double calculateTotal(Order order);
    Optional<Order> getOrderById(Long id)
        throws OrderOperationException;
    List<Order> getByClient(Client client)
        throws OrderOperationException;
    List<Order> getByConsultant(Consultant consultant)
        throws OrderOperationException;
    List<Order> getAllOrders()
        throws OrderOperationException;
    Order getExistingOrder(Long id) 
        throws OrderNotFoundException, OrderOperationException;
}
