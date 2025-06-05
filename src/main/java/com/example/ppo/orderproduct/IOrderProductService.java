package com.example.ppo.orderproduct;

import com.example.ppo.exception.orderproduct.*;
import com.example.ppo.order.Order;
import com.example.ppo.product.Product;
import java.util.List;

public interface IOrderProductService {
    OrderProduct createOrderProduct(Order order, Product product, int quantity)
        throws OrderProductValidationException, OrderProductOperationException;
    OrderProduct updateQuantity(OrderProduct.OrderProductId id, int newQuantity)
        throws OrderProductNotFoundException, OrderProductValidationException, OrderProductOperationException;
    List<OrderProduct> getByOrder(Order order)
        throws OrderProductOperationException;
    void removeOrderProduct(OrderProduct op)
        throws OrderProductNotFoundException, OrderProductOperationException;
    }