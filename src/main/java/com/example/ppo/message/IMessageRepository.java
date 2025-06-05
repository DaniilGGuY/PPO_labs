package com.example.ppo.message;

import java.util.List;

public interface IMessageRepository {
    Message save(Message message);
    List<Message> findByClient_IdAndConsultant_IdOrderByDatetimeDesc(Long clientId, Long consultantId);
    Message update(Message message);
}