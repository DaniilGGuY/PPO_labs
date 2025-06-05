package com.example.ppo.client;

import java.util.Optional;

public interface IClientRepository {
    Client save(Client client);
    Client update(Client client);
    Optional<Client> findByLogin(String login);
    void deleteByLogin(String login);
    boolean existsByLogin(String login);
}