package com.example.ppo.consultant.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.ppo.consultant.Consultant;

import java.util.Optional;
import java.util.List;

@Repository
public interface JpaConsultantRepo extends JpaRepository<Consultant, Long> {

    @Override
    @Transactional
    <S extends Consultant> S save(S consultant);

    @Transactional
    default Consultant update(Consultant consultant) {
        return save(consultant);
    }

    @Transactional
    Optional<Consultant> findByLogin(String login);

    @Transactional
    List<Consultant> findAll();

    @Transactional
    default void deleteByLogin(String login) {
        delete(findByLogin(login).get());
    }

    @Transactional
    boolean existsByLogin(String login);
}