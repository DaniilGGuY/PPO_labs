package com.example.ppo.client.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.ppo.client.Client;

import jakarta.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface MongoClientRepo extends MongoRepository<Client, Long> {

    @Override
    <S extends Client> S save(S client);

    default Client update(Client client) {
        return save(client);
    }

    Optional<Client> findByLogin(String login);

    default void deleteByLogin(String login) {
        findByLogin(login).ifPresent(this::delete);
    }

    boolean existsByLogin(String login);
}