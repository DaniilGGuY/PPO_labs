package com.example.ppo.product;

import com.example.ppo.product.mongo.MongoProductRepo;
import java.util.List;
import java.util.Optional;

public class MongoProductRepoImpl implements IProductRepository {
    private final MongoProductRepo repo;

    public MongoProductRepoImpl(MongoProductRepo repo) {
        this.repo = repo;
    }

    public Product save(Product product) {
        return repo.save(product);
    }

    public Optional<Product> findById(Long id) {
        return repo.findById(id);
    }

    public List<Product> findAll() {
        return repo.findAll();
    }

    public Product update(Product product) {
        return repo.update(product);
    }
}