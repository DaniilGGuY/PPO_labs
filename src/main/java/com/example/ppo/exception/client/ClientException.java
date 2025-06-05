package com.example.ppo.exception.client;

import com.example.ppo.exception.*;

public class ClientException extends BusinessException {
    public ClientException(String msg, String errCode) {
        super(msg, errCode);
    }
}
