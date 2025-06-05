package com.example.ppo.order.mongo;

import com.example.ppo.exception.order.*;
import com.example.ppo.order.Order;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MongoOrderRepo extends MongoRepository<Order, Long> {

    @Override
    <S extends Order> S save(S order) throws OrderOperationException;

    @Override
    Optional<Order> findById(Long id);

    List<Order> findByClient_Id(Long clientId);

    List<Order> findByConsultant_Id(Long consultantId);

    @Override
    List<Order> findAll();

    default Order update(Order order) {
        return save(order);
    }

    boolean existsById(Long id);
}