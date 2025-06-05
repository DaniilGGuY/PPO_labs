package com.example.ppo.orderproduct.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.ppo.orderproduct.OrderProduct;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaOrderProductRepo extends JpaRepository<OrderProduct, OrderProduct.OrderProductId> {

    @Override
    @Transactional
    <S extends OrderProduct> S save(S orderProduct);

    @Override
    @Transactional(readOnly = true)
    Optional<OrderProduct> findById(OrderProduct.OrderProductId id);

    @Transactional(readOnly = true)
    List<OrderProduct> findByOrderId(Long orderId);

    @Transactional
    default OrderProduct update(OrderProduct op) {
        return save(op);
    }

    @Override
    @Transactional
    void delete(OrderProduct op);
}