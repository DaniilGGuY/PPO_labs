package com.example.ppo.exception.orderproduct;

public class OrderProductNotFoundException extends OrderProductException {
    public OrderProductNotFoundException(Long orderId, Long productId) {
        super(
            String.format("OrderProduct with orderId=%d and productId=%d not found", orderId, productId),
            "ORDER_PRODUCT_NOT_FOUND"
        );
    }
}