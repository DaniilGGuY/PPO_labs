package com.example.ppo.exception.consultant;

import com.example.ppo.exception.*;

public class ConsultantException extends BusinessException {
    public ConsultantException(String msg, String errCode) {
        super(msg, errCode);
    }
}
