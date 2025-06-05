package com.example.ppo.exception.manager;

public class ManagerOperationException extends ManagerException {
    public ManagerOperationException(String msg) {
        super(msg, "MANAGER_OPERATION_ERROR");
    }
}