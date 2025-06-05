package com.example.ppo.orderproduct;

import com.example.ppo.orderproduct.jpa.JpaOrderProductRepo;

import java.util.List;
import java.util.Optional;

public class JpaOrderProductRepoImpl implements IOrderProductRepository {
    private final JpaOrderProductRepo repo;

    public JpaOrderProductRepoImpl(JpaOrderProductRepo repo) {
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