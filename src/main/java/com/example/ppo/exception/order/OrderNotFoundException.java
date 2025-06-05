package com.example.ppo.exception.order;

public class OrderNotFoundException extends OrderException {
    public OrderNotFoundException(Long orderId) {
        super(String.format("Order with id %d not found", orderId), "ORDER_NOT_FOUND");
    }
}