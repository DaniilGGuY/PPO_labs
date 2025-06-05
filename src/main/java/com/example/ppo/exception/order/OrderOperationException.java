package com.example.ppo.exception.order;

public class OrderOperationException extends OrderException {
    public OrderOperationException(String msg) {
        super(msg, "ORDER_OPERATION_ERROR");
    }
}