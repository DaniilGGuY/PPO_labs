package com.example.ppo.manager;

import java.util.Optional;

public interface IManagerRepository {
    Manager save(Manager consultant);
    Manager update(Manager consultant);
    Optional<Manager> findByLogin(String login);
    void deleteByLogin(String login);
    boolean existsByLogin(String login);
}