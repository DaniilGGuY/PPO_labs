package com.example.ppo.exception.manager;

public class ManagerNotFoundException extends ManagerException {
    public ManagerNotFoundException(String login) {
        super(String.format("Manager with login %s not found", login), "MANAGER_NOT_FOUND");
    }
}