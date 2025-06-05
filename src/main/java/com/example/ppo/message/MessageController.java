package com.example.ppo.message;

import com.example.ppo.exception.message.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/messages")
@Tag(name = "Message Management", description = "APIs for managing messages")
@Slf4j
public class MessageController implements IMessageController {

    private final IMessageService messageService;

    public MessageController(IMessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Send a message", description = "Sends a new message between client and consultant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Message sent", content = @Content(schema = @Schema(implementation = Message.class))),
        @ApiResponse(responseCode = "400", description = "Invalid message data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody Message message) {
        try {
            log.info("POST /api/messages");
            Message sentMessage = messageService.sendMessage(message);
            return ResponseEntity.status(HttpStatus.CREATED).body(sentMessage);
        } catch (MessageValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (MessageOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Get chat history", description = "Retrieves chat history between client and consultant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Chat messages retrieved", content = @Content(schema = @Schema(implementation = Message[].class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/chat")
    public ResponseEntity<?> getChat(@RequestParam Long clientId, @RequestParam Long consultantId) { 
        try {
            log.info("POST /api/messages/chat");
            List<Message> messages = messageService.getClientConsultantChat(clientId, consultantId);
            return ResponseEntity.ok(messages);
        } catch (MessageOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Update message", description = "Updates an existing message")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Message updated", content = @Content(schema = @Schema(implementation = Message.class))),
        @ApiResponse(responseCode = "400", description = "Invalid message data"),
        @ApiResponse(responseCode = "404", description = "Message not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMessage(@PathVariable Long id, @RequestBody Message message) {
        try {
            log.info("POST /api/messages/{}", id);
            message.setId(id);
            Message updatedMessage = messageService.updateMessage(message);
            return ResponseEntity.ok(updatedMessage);
        } catch (MessageValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (MessageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (MessageOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}