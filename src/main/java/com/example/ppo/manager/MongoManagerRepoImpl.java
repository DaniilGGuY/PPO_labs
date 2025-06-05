package com.example.ppo.manager;

import com.example.ppo.manager.mongo.MongoManagerRepo;

import java.util.Optional;

public class MongoManagerRepoImpl implements IManagerRepository {
    private final MongoManagerRepo repo;

    public MongoManagerRepoImpl(MongoManagerRepo repo) {
        this.repo = repo;
    }

    public Manager save(Manager Manager) {
        return repo.save(Manager);
    }

    public Manager update(Manager Manager) {
        return repo.update(Manager);
    }

    public Optional<Manager> findByLogin(String login) {
        return repo.findByLogin(login);
    }

    public void deleteByLogin(String login) {
        repo.deleteByLogin(login);
    }

    public boolean existsByLogin(String login) {
        return repo.existsByLogin(login);
    }
}