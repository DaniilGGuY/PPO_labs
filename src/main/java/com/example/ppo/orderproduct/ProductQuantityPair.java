package com.example.ppo.orderproduct;

import com.example.ppo.product.*;

public class ProductQuantityPair {
    private Product product;
    private Integer quantity;

    public ProductQuantityPair(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setProduct(Product p) {
        this.product = p;
    }

    public void setQuantity(Integer q) {
        this.quantity = q;
    }
}