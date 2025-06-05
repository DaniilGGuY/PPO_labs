package com.example.ppo.client.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.ppo.client.Client;

import java.util.Optional;

@Repository
public interface JpaClientRepo extends JpaRepository<Client, Long> {

    @Override
    @Transactional
    <S extends Client> S save(S client);

    @Transactional
    default Client update(Client client) {
        return save(client);
    }

    @Transactional
    Optional<Client> findByLogin(String login);

    @Transactional
    default void deleteByLogin(String login) {
        delete(findByLogin(login).get());
    }

    @Transactional
    boolean existsByLogin(String login);
}