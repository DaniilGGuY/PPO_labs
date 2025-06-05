package com.example.ppo.product.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.ppo.product.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface MongoProductRepo extends MongoRepository<Product, Long> {

    @Override
    <S extends Product> S save(S product);

    @Override
    Optional<Product> findById(Long id);

    @Override
    List<Product> findAll();

    default Product update(Product product) {
        return save(product);
    }
}