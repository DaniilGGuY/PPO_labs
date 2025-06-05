package com.example.ppo.message;

import com.example.ppo.message.jpa.JpaMessageRepo;
import java.util.List;

public class JpaMessageRepoImpl implements IMessageRepository {
    private final JpaMessageRepo repo;

    public JpaMessageRepoImpl(JpaMessageRepo repo) {
        this.repo = repo;
    }

    public Message save(Message message) {
        return repo.save(message);
    }

    public List<Message> findByClient_IdAndConsultant_IdOrderByDatetimeDesc(Long clientId, Long consultantId) {
        return repo.findByClient_IdAndConsultant_IdOrderByDatetimeDesc(clientId, consultantId);
    }

    public Message update(Message message) {
        return repo.update(message);
    }
}