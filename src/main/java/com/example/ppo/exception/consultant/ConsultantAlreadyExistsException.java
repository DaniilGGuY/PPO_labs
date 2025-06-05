package com.example.ppo.exception.consultant;

public class ConsultantAlreadyExistsException extends ConsultantException {
    public ConsultantAlreadyExistsException(String login) {
        super(String.format("Consultant with login %s already exists", login), "CONSULTANT_ALREADY_EXISTS");
    }
}