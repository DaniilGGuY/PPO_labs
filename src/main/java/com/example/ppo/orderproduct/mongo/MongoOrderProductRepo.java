package com.example.ppo.orderproduct.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.ppo.orderproduct.OrderProduct;

import java.util.List;
import java.util.Optional;

@Repository
public interface MongoOrderProductRepo extends MongoRepository<OrderProduct, OrderProduct.OrderProductId> {

    @Override
    <S extends OrderProduct> S save(S orderProduct);

    @Override
    Optional<OrderProduct> findById(OrderProduct.OrderProductId id);

    List<OrderProduct> findByOrderId(Long orderId);

    default OrderProduct update(OrderProduct op) {
        return save(op);
    }

    @Override
    void delete(OrderProduct op);
}