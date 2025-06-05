package com.example.ppo.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface IClientController {
    ResponseEntity<?> registerClient(@RequestBody Client client);

    ResponseEntity<?> getClientByLogin(@PathVariable String login);

    ResponseEntity<?> updateClient(@RequestBody Client client);

    ResponseEntity<?> deleteClient(@PathVariable String login);

    ResponseEntity<?> checkClientExists(@PathVariable String login);
}