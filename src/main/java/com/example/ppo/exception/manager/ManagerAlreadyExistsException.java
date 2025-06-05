package com.example.ppo.exception.manager;

public class ManagerAlreadyExistsException extends ManagerException {
    public ManagerAlreadyExistsException(String login) {
        super(String.format("Manager with login %s already exists", login), "MANAGER_ALREADY_EXISTS");
    }
}