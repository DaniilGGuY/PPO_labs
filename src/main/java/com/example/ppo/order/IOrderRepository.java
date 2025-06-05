package com.example.ppo.order;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository {
    Order save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findByClient_Id(Long clientId);
    List<Order> findByConsultant_Id(Long consultantId);
    List<Order> findAll();
    Order update(Order order);
    boolean existsById(Long id);
}