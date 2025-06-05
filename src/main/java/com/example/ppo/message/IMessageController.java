package com.example.ppo.message;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface IMessageController {
    ResponseEntity<?> sendMessage(@RequestBody Message message);
    
    ResponseEntity<?> getChat(@RequestParam Long clientId, @RequestParam Long consultantId);
    
    ResponseEntity<?> updateMessage(@PathVariable Long id, @RequestBody Message message);
}