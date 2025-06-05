package com.example.ppo.manager;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface IManagerController {
    ResponseEntity<?> registerManager(@RequestBody Manager manager);

    ResponseEntity<?> getManagerByLogin(@PathVariable String login);

    ResponseEntity<?> updateManager(@RequestBody Manager manager);

    ResponseEntity<?> deleteManager(@PathVariable String login);

    ResponseEntity<?> checkManagerExists(@PathVariable String login);
}