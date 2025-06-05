package com.example.ppo.consultant;

import com.example.ppo.consultant.jpa.JpaConsultantRepo;

import java.util.List;
import java.util.Optional;

public class JpaConsultantRepoImpl implements IConsultantRepository {
    private final JpaConsultantRepo repo;

    public JpaConsultantRepoImpl(JpaConsultantRepo repo) {
        this.repo = repo;
    }

    public Consultant save(Consultant consultant) {
        return repo.save(consultant);
    }

    public Consultant update(Consultant consultant) {
        return repo.update(consultant);
    }

    public Optional<Consultant> findByLogin(String login) {
        return repo.findByLogin(login);
    }

    public List<Consultant> findAll() {
        return repo.findAll();
    }

    public void deleteByLogin(String login) {
        repo.deleteByLogin(login);
    }

    public boolean existsByLogin(String login) {
        return repo.existsByLogin(login);
    }
}