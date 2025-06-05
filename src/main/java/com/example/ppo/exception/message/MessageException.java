package com.example.ppo.exception.message;

import com.example.ppo.exception.*;

public class MessageException extends BusinessException {
    public MessageException(String msg, String errCode) {
        super(msg, errCode);
    }
}
