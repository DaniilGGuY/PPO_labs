package com.example.ppo.consultant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface IConsultantController {
    ResponseEntity<?> registerConsultant(@RequestBody Consultant consultant);

    ResponseEntity<?> getConsultantByLogin(@PathVariable String login);

    ResponseEntity<?> updateConsultant(@RequestBody Consultant consultant);

    ResponseEntity<?> deleteConsultant(@PathVariable String login);

    ResponseEntity<?> checkConsultantExists(@PathVariable String login);

    ResponseEntity<?> findAllConsultants();
}