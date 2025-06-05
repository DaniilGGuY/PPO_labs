package com.example.ppo.orderproduct;

import java.util.List;
import java.util.Optional;

public interface IOrderProductRepository {
    OrderProduct save(OrderProduct orderProduct);
    Optional<OrderProduct> findById(OrderProduct.OrderProductId id);
    List<OrderProduct> findByOrderId(Long orderId);
    OrderProduct update(OrderProduct op);
    void delete(OrderProduct op);
}