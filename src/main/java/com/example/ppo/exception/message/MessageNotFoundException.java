package com.example.ppo.exception.message;

public class MessageNotFoundException extends MessageException {
    public MessageNotFoundException(Long messageId) {
        super(String.format("Message with id %d not found", messageId), "MESSAGE_NOT_FOUND");
    }
}