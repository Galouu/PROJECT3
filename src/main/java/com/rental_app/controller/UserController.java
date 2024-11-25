package com.rental_app.controller;

import com.rental_app.dto.UserDTO;
import com.rental_app.model.User;
import com.rental_app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Inscrire un utilisateur", description = "Cette route permet de créer un nouvel utilisateur en fournissant un email, un nom et un mot de passe.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur inscrit avec succès",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))}),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody User user) {
        UserDTO newUser = userService.createUser(user.getEmail(), user.getName(), user.getPassword());
        return ResponseEntity.ok(newUser);
    }

    @Operation(summary = "Connecter un utilisateur", description = "Cette route permet à un utilisateur de se connecter en fournissant son email et son mot de passe.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Connexion réussie",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))}),
        @ApiResponse(responseCode = "401", description = "Identifiants invalides")
    })
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserDTO userDTO) {
        Optional<UserDTO> userOptional = userService.findByEmail(userDTO.getEmail());
        if (userOptional.isEmpty() || !userService.checkPassword(userDTO.getPassword(), userOptional.get().getPassword())) {
            return ResponseEntity.status(401).body("Identifiants invalides");
        }
        return ResponseEntity.ok("Connexion réussie");
    }
}
