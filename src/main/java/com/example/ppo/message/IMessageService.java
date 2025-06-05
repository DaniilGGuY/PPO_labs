package com.example.ppo.message;

import com.example.ppo.exception.message.*;
import java.util.List;

public interface IMessageService {
    Message sendMessage(Message message) 
        throws MessageValidationException, MessageOperationException;
    List<Message> getClientConsultantChat(Long clientId, Long consultantId)
        throws MessageOperationException;
    Message updateMessage(Message message) 
        throws MessageNotFoundException, MessageValidationException, MessageOperationException;
}