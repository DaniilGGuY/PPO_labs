package com.example.ppo.exception.orderproduct;

import com.example.ppo.exception.*;

public class OrderProductException extends BusinessException {
    public OrderProductException(String msg, String errCode) {
        super(msg, errCode);
    }
}
