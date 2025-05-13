package com.example.ppo.order;

import com.example.ppo.client.Client;
import com.example.ppo.consultant.Consultant;
import com.example.ppo.exception.BusinessException;
import com.example.ppo.orderproduct.OrderProduct;
import java.util.List;

public interface IOrderService {
	Order createOrder(Client client, List<OrderProduct> items) throws BusinessException;
    public Order assignConsultant(Order order, Consultant consultant) throws BusinessException;
    double calculateTotal(Order order);
    Order getOrderById(long id);
}
