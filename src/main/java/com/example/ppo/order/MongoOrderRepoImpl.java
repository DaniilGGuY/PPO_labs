package com.example.ppo.order;

import com.example.ppo.order.mongo.MongoOrderRepo;
import java.util.*;

public class MongoOrderRepoImpl implements IOrderRepository {

    private final MongoOrderRepo repo;

    public MongoOrderRepoImpl(MongoOrderRepo repo) {
        this.repo = repo;
    }

    public Order save(Order order) {
        return repo.save(order);
    }

    public Optional<Order> findById(Long id) {
        return repo.findById(id);
    }

    public List<Order> findByClient_Id(Long clientId) {
        return repo.findByClient_Id(clientId);
    }

    public List<Order> findByConsultant_Id(Long consultantId) {
        return repo.findByConsultant_Id(consultantId);
    }

    public List<Order> findAll() {
        return repo.findAll();
    }

    public Order update(Order order) {
        return repo.update(order);
    }

    public boolean existsById(Long id) {
        return repo.existsById(id);
    }
}
