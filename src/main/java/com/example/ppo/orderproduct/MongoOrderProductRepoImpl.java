package com.example.ppo.orderproduct;

import com.example.ppo.orderproduct.mongo.MongoOrderProductRepo;

import java.util.List;
import java.util.Optional;

public class MongoOrderProductRepoImpl implements IOrderProductRepository {
    private final MongoOrderProductRepo repo;

    public MongoOrderProductRepoImpl(MongoOrderProductRepo repo) {
        this.repo = repo;
    }

    public OrderProduct save(OrderProduct orderProduct) {
        return repo.save(orderProduct);
    }

    public Optional<OrderProduct> findById(OrderProduct.OrderProductId id) {
        return repo.findById(id);
    }

    public List<OrderProduct> findByOrderId(Long orderId) {
        return repo.findByOrderId(orderId);
    }

    public OrderProduct update(OrderProduct op) {
        return repo.update(op);
    }

    public void delete(OrderProduct op) {
        repo.delete(op);
    }
}