package com.example.ppo.order.jpa;

import com.example.ppo.order.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaOrderRepo extends JpaRepository<Order, Long> {

    @Override
    @Transactional
    <S extends Order> S save(S order);

    @Override
    @Transactional
    Optional<Order> findById(Long id);

    @Transactional
    List<Order> findByClient_Id(Long clientId);

    @Transactional
    List<Order> findByConsultant_Id(Long consultantId);

    @Override
    @Transactional
    List<Order> findAll();

    @Transactional
    default Order update(Order order) {
        return save(order);
    }

    @Transactional
    boolean existsById(Long id);
}