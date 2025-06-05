package com.example.ppo.exception.client;

public class ClientAlreadyExistsException extends ClientException {
    public ClientAlreadyExistsException(String login) {
        super(String.format("Client with login %s already exists", login), "CLIENT_ALREADY_EXISTS");
    }
}