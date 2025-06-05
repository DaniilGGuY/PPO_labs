package com.example.ppo.message;

import com.example.ppo.exception.message.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class MessageService implements IMessageService {
    private final IMessageRepository msgRepo;

    public MessageService(IMessageRepository msgRepo) {
        this.msgRepo = msgRepo;
    }

    @Override
    @Transactional
    public Message sendMessage(Message message) 
            throws MessageValidationException, MessageOperationException {
        if (!message.isValid()) {
            log.warn("Invalid message");
            throw new MessageValidationException("Invalid message data");
        }
        
        try {
            message = msgRepo.save(message);
            if (message.getIsClient().equals(Boolean.TRUE)) {
                message.getClient().addMessage(message);
                log.info("{} send message to {}", message.getClient().getLogin(), message.getConsultant().getLogin());
            } else {
                message.getConsultant().addMessage(message);
                log.info("{} send message to {}", message.getConsultant().getLogin(), message.getClient().getLogin());
            }
            
            return message;
        } catch (Exception e) {
            log.error("Failed to send message", e);
            throw new MessageOperationException("Failed to send message: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Message> getClientConsultantChat(Long clientId, Long consultantId) 
            throws MessageOperationException {
        try {
            return msgRepo.findByClient_IdAndConsultant_IdOrderByDatetimeDesc(clientId, consultantId);
        } catch (Exception e) {
            log.error("Failed to find chat", e);
            throw new MessageOperationException("Failed to find message: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Message updateMessage(Message message) 
            throws MessageNotFoundException, MessageValidationException, MessageOperationException {
        if (!message.isValid()) {
            log.warn("Invalid message");
            throw new MessageValidationException("Invalid message data");
        }

        try {
            message = msgRepo.update(message);
            log.info("Successfully updated message with id={}", message.getId());
            return message;
        } catch (Exception e) {
            log.error("Failed to send message", e);
            throw new MessageOperationException("Failed to update message: " + e.getMessage());
        }
    }
}