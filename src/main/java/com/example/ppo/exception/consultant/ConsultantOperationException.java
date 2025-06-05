package com.example.ppo.exception.consultant;

public class ConsultantOperationException extends ConsultantException {
    public ConsultantOperationException(String msg) {
        super(msg, "COSULTANT_OPERATION_ERROR");
    }
}