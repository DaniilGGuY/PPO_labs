package com.example.ppo.product.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.ppo.product.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaProductRepo extends JpaRepository<Product, Long> {

    @Override
    @Transactional
    <S extends Product> S save(S product);

    @Override
    @Transactional(readOnly = true)
    Optional<Product> findById(Long id);

    @Override
    @Transactional(readOnly = true)
    List<Product> findAll();

    @Transactional
    default Product update(Product product) {
        return save(product);
    }
}