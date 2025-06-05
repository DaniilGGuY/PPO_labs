package com.example.ppo.exception.review;

import com.example.ppo.exception.*;

public class ReviewException extends BusinessException {
    public ReviewException(String msg, String errCode) {
        super(msg, errCode);
    }
}
