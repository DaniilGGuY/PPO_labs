package com.example.ppo.message;

import com.example.ppo.message.mongo.MongoMessageRepo;
import java.util.List;

public class MongoMessageRepoImpl implements IMessageRepository {
    private final MongoMessageRepo repo;

    public MongoMessageRepoImpl(MongoMessageRepo repo) {
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