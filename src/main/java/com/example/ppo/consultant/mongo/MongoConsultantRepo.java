package com.example.ppo.consultant.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.ppo.consultant.Consultant;

import java.util.Optional;
import java.util.List;

@Repository
public interface MongoConsultantRepo extends MongoRepository<Consultant, Long> {

    @Override
    <S extends Consultant> S save(S consultant);

    default Consultant update(Consultant consultant) {
        return save(consultant);
    }

    Optional<Consultant> findByLogin(String login);

    List<Consultant> findAll();

    default void deleteByLogin(String login) {
        delete(findByLogin(login).get());
    }

    boolean existsByLogin(String login);
}