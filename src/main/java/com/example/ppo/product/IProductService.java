package com.example.ppo.product;

import com.example.ppo.exception.product.*;
import java.util.List;

public interface IProductService {
    Product createProduct(Product product) 
        throws ProductValidationException, ProductOperationException;
    Product updateProduct(Product product) 
        throws ProductNotFoundException, ProductValidationException, ProductOperationException;   
    List<Product> getAllProducts()
        throws ProductOperationException;
    Product getExistingProduct(Long id) 
        throws ProductNotFoundException, ProductOperationException;
}