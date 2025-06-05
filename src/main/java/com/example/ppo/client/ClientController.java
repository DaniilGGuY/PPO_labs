package com.example.ppo.client;

import com.example.ppo.exception.client.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "Client Management", description = "APIs for managing clients")
@Slf4j
public class ClientController implements IClientController {

    @Autowired
    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "Register a new client", description = "Creates a new client account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Client created", content = @Content(schema = @Schema(implementation = Client.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "409", description = "Client already exists"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> registerClient(@RequestBody Client client) {
        try {
            log.info("POST /api/clients");
            Client registeredClient = clientService.registerClient(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredClient);
        } catch (ClientValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ClientAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (ClientOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Get client by login", description = "Returns a single client by login")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Client found", content = @Content(schema = @Schema(implementation = Client.class))),
        @ApiResponse(responseCode = "404", description = "Client not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{login}")
    public ResponseEntity<?> getClientByLogin(@PathVariable String login) {
        try {
            log.info("GET /api/clients/{}", login);
            Client client = clientService.getClientByLogin(login);
            return ResponseEntity.ok(client);
        } catch (ClientNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ClientOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Update client", description = "Updates an existing client")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Client updated", content = @Content(schema = @Schema(implementation = Client.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Client not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping
    public ResponseEntity<?> updateClient(@RequestBody Client client) {
        try {
            log.info("PUT /api/clients");
            Client updatedClient = clientService.updateClient(client);
            return ResponseEntity.ok(updatedClient);
        } catch (ClientValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ClientNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ClientOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Delete client", description = "Deletes a client by login")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Client deleted"),
        @ApiResponse(responseCode = "404", description = "Client not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{login}")
    public ResponseEntity<?> deleteClient(@PathVariable String login) {
        try {
            log.info("DELETE /api/clients/{}", login);
            clientService.deleteClient(login);
            return ResponseEntity.noContent().build();
        } catch (ClientNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ClientOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Check client existence", description = "Checks if a client exists by login")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Existence check result", content = @Content(schema = @Schema(implementation = Boolean.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/exists/{login}")
    public ResponseEntity<?> checkClientExists(@PathVariable String login) {
        try {
            log.info("GET /api/clients/exists/{}", login);
            boolean exists = clientService.clientExists(login);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}