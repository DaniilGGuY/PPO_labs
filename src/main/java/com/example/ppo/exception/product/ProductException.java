package com.example.ppo.exception.product;

import com.example.ppo.exception.*;

public class ProductException extends BusinessException {
    public ProductException(String msg, String errCode) {
        super(msg, errCode);
    }
}
