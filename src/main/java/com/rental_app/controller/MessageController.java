package com.rental_app.controller;

import com.rental_app.dto.MessageDTO;
import com.rental_app.model.Message;
import com.rental_app.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Operation(summary = "Créer un message", description = "Crée un message pour un utilisateur et une location donnés.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Message créé avec succès",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDTO.class))}),
        @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PostMapping
    public ResponseEntity<MessageDTO> createMessage(@Valid @RequestBody Message message) {
        MessageDTO createdMessage = messageService.createMessage(message);
        return ResponseEntity.status(201).body(createdMessage);
    }
}
