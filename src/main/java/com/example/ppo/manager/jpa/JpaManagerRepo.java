package com.example.ppo.manager.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.ppo.manager.Manager;

import java.util.Optional;

@Repository
public interface JpaManagerRepo extends JpaRepository<Manager, Long> {

    @Override
    @Transactional
    <S extends Manager> S save(S manager);

    @Transactional
    default Manager update(Manager manager) {
        return save(manager);
    }

    @Transactional
    Optional<Manager> findByLogin(String login);

    @Transactional
    default void deleteByLogin(String login) {
        delete(findByLogin(login).get());
    }

    @Transactional
    boolean existsByLogin(String login);
}