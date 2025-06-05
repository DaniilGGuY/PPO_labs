package com.example.ppo.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface IProductController {
    ResponseEntity<?> createProduct(@RequestBody Product product);

    ResponseEntity<?> updateProduct(@RequestBody Product product);

    ResponseEntity<?> getAllProducts();
}