package com.example.ppo.manager;

import com.example.ppo.exception.manager.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/managers")
@Tag(name = "Manager management", description = "APIs for managing managers")
@Slf4j
public class ManagerController implements IManagerController {

    private final IManagerService managerService;

    public ManagerController(IManagerService managerService) {
        this.managerService = managerService;
    }

    @Operation(summary = "Register a new manager", description = "Creates a new manager account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Manager created", content = @Content(schema = @Schema(implementation = Manager.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "409", description = "Manager already exists"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> registerManager(@RequestBody Manager manager) {
        try {
            log.info("POST /api/managers");
            Manager registeredManager = managerService.registerManager(manager);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredManager);
        } catch (ManagerValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ManagerAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (ManagerOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Get manager by login", description = "Returns a single manager by login")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Manager found", content = @Content(schema = @Schema(implementation = Manager.class))),
        @ApiResponse(responseCode = "404", description = "Manager not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{login}")
    public ResponseEntity<?> getManagerByLogin(@PathVariable String login) {
        try {
            log.info("GET /api/managers/{}", login);
            Manager manager = managerService.getManagerByLogin(login);
            return ResponseEntity.ok(manager);
        } catch (ManagerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ManagerOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Update manager", description = "Updates an existing manager")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Manager updated", content = @Content(schema = @Schema(implementation = Manager.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Manager not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping
    public ResponseEntity<?> updateManager(@RequestBody Manager manager) {
        try {
            log.info("PUT /api/managers");
            Manager updatedManager = managerService.updateManager(manager);
            return ResponseEntity.ok(updatedManager);
        } catch (ManagerValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ManagerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ManagerOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Delete manager", description = "Deletes a manager by login")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Manager deleted"),
        @ApiResponse(responseCode = "404", description = "Manager not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{login}")
    public ResponseEntity<?> deleteManager(@PathVariable String login) {
        try {
            log.info("DELETE /api/managers/{}", login);
            managerService.deleteManager(login);
            return ResponseEntity.noContent().build();
        } catch (ManagerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ManagerOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Check manager existence", description = "Checks if a manager exists by login")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Existence check result", content = @Content(schema = @Schema(implementation = Boolean.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/exists/{login}")
    public ResponseEntity<?> checkManagerExists(@PathVariable String login) {
        try {
            log.info("GET /api/managers/exists/{}", login);
            boolean exists = managerService.managerExists(login);
            return ResponseEntity.ok(exists);
        } catch (ManagerOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}