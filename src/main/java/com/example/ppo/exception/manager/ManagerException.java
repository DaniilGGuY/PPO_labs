package com.example.ppo.exception.manager;

import com.example.ppo.exception.*;

public class ManagerException extends BusinessException {
    public ManagerException(String msg, String errCode) {
        super(msg, errCode);
    }
}
