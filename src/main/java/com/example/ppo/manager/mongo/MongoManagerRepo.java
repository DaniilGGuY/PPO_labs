package com.example.ppo.manager.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.ppo.manager.Manager;

import java.util.Optional;

@Repository
public interface MongoManagerRepo extends MongoRepository<Manager, Long> {

    @Override
    <S extends Manager> S save(S manager);

    default Manager update(Manager manager) {
        return save(manager);
    }

    Optional<Manager> findByLogin(String login);

    default void deleteByLogin(String login) {
        delete(findByLogin(login).get());
    }

    boolean existsByLogin(String login);
}