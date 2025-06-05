package com.example.ppo.product;

import com.example.ppo.exception.product.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Service
@Transactional
@Slf4j
public class ProductService implements IProductService {

    private final IProductRepository prodRepo;

    public ProductService(IProductRepository prodRepo) {
        this.prodRepo = prodRepo;
    }

    @Override
    public Product createProduct(Product product) 
            throws ProductValidationException, ProductOperationException {
        if (!product.isValid()) {
            log.warn("Invalid product");
            throw new ProductValidationException("Invalid product data");
        }

        try {
            product = prodRepo.save(product);
            log.info("Created product with id={}", product.getId());
            return product;
        } catch (Exception e) {
            log.error("Failed to save product", e);
            throw new ProductOperationException("Failed to create product: " + e.getMessage());
        }
    }

    @Override
    public Product updateProduct(Product product) 
            throws ProductNotFoundException, ProductValidationException, ProductOperationException {
        if (!product.isValid()) {
            log.warn("Invalid product");
            throw new ProductValidationException("Invalid product data");
        }

        try {
            product = prodRepo.update(product);
            log.info("Updated product with id={}", product.getId());
            return product;
        } catch (Exception e) {
            log.error("Failed to update product", e);
            throw new ProductOperationException("Failed to update product: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() throws ProductOperationException {
        try {
            return prodRepo.findAll();
        } catch (Exception e) {
            log.error("Failed to find products", e);
            throw new ProductOperationException("Failed to find products: " + e.getMessage());
        }
    }

    @Override
    public Product getExistingProduct(Long id) 
            throws ProductNotFoundException, ProductOperationException {
        try {
            return prodRepo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        } catch (Exception e) {
            log.error("Failed to get product", e);
            throw new ProductOperationException("Failed to get product: " + e.getMessage());
        }
    }
}