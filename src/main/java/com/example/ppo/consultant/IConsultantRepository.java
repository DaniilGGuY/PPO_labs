package com.example.ppo.consultant;

import java.util.List;
import java.util.Optional;

public interface IConsultantRepository {
    Consultant save(Consultant consultant);
    Consultant update(Consultant consultant);
    Optional<Consultant> findByLogin(String login);
    List<Consultant> findAll();
    void deleteByLogin(String login);
    boolean existsByLogin(String login);
}