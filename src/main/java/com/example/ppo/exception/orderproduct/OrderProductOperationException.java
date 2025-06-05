package com.example.ppo.exception.orderproduct;

public class OrderProductOperationException extends OrderProductException {
    public OrderProductOperationException(String msg) {
        super(msg, "ORDER_PRODUCT_OPERATION_ERROR");
    }
}