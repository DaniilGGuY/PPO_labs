package com.example.ppo.consultant;

import com.example.ppo.exception.consultant.*;
import com.example.ppo.product.Product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/consultants")
@Tag(name = "Consultant Management", description = "APIs for managing consultants")
@Slf4j
public class ConsultantController implements IConsultantController {

    private final IConsultantService consultantService;

    public ConsultantController(IConsultantService consultantService) {
        this.consultantService = consultantService;
    }

    @Operation(summary = "Register a new consultant", description = "Creates a new consultant account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Consultant created", content = @Content(schema = @Schema(implementation = Consultant.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "409", description = "Consultant already exists"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> registerConsultant(@RequestBody Consultant consultant) {
        try {
            log.info("POST /api/consultants");
            Consultant registeredConsultant = consultantService.registerConsultant(consultant);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredConsultant);
        } catch (ConsultantValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ConsultantAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (ConsultantOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Get consultant by login", description = "Returns a single consultant by login")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consultant found", content = @Content(schema = @Schema(implementation = Consultant.class))),
        @ApiResponse(responseCode = "404", description = "Consultant not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{login}")
    public ResponseEntity<?> getConsultantByLogin(@PathVariable String login) {
        try {
            log.info("GET /api/consultants/{}", login);
            Consultant consultant = consultantService.getConsultantByLogin(login);
            return ResponseEntity.ok(consultant);
        } catch (ConsultantNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ConsultantOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Update consultant", description = "Updates an existing consultant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consultant updated", content = @Content(schema = @Schema(implementation = Consultant.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Consultant not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping
    public ResponseEntity<?> updateConsultant(@RequestBody Consultant consultant) {
        try {
            log.info("PUT /api/consultants");
            Consultant updatedConsultant = consultantService.updateConsultant(consultant);
            return ResponseEntity.ok(updatedConsultant);
        } catch (ConsultantValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ConsultantNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ConsultantOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Delete consultant", description = "Deletes a consultant by login")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Consultant deleted"),
        @ApiResponse(responseCode = "404", description = "Consultant not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{login}")
    public ResponseEntity<?> deleteConsultant(@PathVariable String login) {
        try {
            log.info("DELETE /api/consultants/{}", login);
            consultantService.deleteConsultant(login);
            return ResponseEntity.noContent().build();
        } catch (ConsultantNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ConsultantOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Check consultant existence", description = "Checks if a consultant exists by login")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Existence check result", content = @Content(schema = @Schema(implementation = Boolean.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/exists/{login}")
    public ResponseEntity<?> checkConsultantExists(@PathVariable String login) {
        try {
            log.info("GET /api/consultants/exists/{}", login);
            boolean exists = consultantService.consultantExists(login);
            return ResponseEntity.ok(exists);
        } catch (ConsultantOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Get all consultants", description = "Returns list of all working consultants")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of consultants", content = @Content(schema = @Schema(implementation = Product[].class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<?> findAllConsultants() {
        try {
            log.info("GET /api/consultants");
            List<Consultant> consultants = consultantService.findAllConsultants();
            return ResponseEntity.ok(consultants);
        } catch (ConsultantOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}