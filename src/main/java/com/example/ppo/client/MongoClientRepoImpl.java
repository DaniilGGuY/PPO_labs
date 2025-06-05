package com.example.ppo.client;

import com.example.ppo.client.mongo.MongoClientRepo;

import java.util.Optional;

public class MongoClientRepoImpl implements IClientRepository {
    private final MongoClientRepo repo;

    public MongoClientRepoImpl(MongoClientRepo repo) {
        this.repo = repo;
    }

    public Client save(Client client) {
        return repo.save(client);
    }

    public Client update(Client client) {
        return repo.update(client);
    }

    public Optional<Client> findByLogin(String login) {
        return repo.findByLogin(login);
    }

    public void deleteByLogin(String login) {
        repo.deleteByLogin(login);
    }

    public boolean existsByLogin(String login) {
        return repo.existsByLogin(login);
    }
}