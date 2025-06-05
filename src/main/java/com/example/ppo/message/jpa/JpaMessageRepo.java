package com.example.ppo.message.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.ppo.message.Message;

import java.util.List;

@Repository
public interface JpaMessageRepo extends JpaRepository<Message, Long> {

    @Override
    @Transactional
    <S extends Message> S save(S message);

    @Transactional
    List<Message> findByClient_IdAndConsultant_IdOrderByDatetimeDesc(Long clientId, Long consultantId);

    @Modifying
    @Transactional
    default Message update(Message message) {
        return save(message);
    }
}