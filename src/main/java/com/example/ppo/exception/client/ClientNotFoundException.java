package com.example.ppo.exception.client;

public class ClientNotFoundException extends ClientException {
    public ClientNotFoundException(String login) {
        super(String.format("Client with login %s not found", login), "CLIENT_NOT_FOUND");
    }
}
