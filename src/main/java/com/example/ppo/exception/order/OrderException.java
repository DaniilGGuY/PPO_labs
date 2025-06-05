package com.example.ppo.exception.order;

import com.example.ppo.exception.*;

public class OrderException extends BusinessException {
    public OrderException(String msg, String errCode) {
        super(msg, errCode);
    }
}

