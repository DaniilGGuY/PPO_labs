package com.example.ppo.message.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.example.ppo.message.Message;

import java.util.List;

@Repository
public interface MongoMessageRepo extends MongoRepository<Message, Long> {

    @Override
    <S extends Message> S save(S message);

    List<Message> findByClient_IdAndConsultant_IdOrderByDatetimeDesc(Long clientId, Long consultantId);

    @Modifying
    default Message update(Message message) {
        return save(message);
    }
}