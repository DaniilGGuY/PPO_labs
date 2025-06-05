package com.example.ppo.exception.consultant;

public class ConsultantNotFoundException extends ConsultantException {
    public ConsultantNotFoundException(String login) {
        super(String.format("Consultant with login %s not found", login), "CONSULTANT_NOT_FOUND");
    }
}