package com.rental_app.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rental_app.dto.UserDTO;
import com.rental_app.service.JwtService;
import com.rental_app.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO newUser = userService.createUser(userDTO.getEmail(), userDTO.getName(), userDTO.getPassword());
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        Optional<UserDTO> userOptional = userService.findByEmail(userDTO.getEmail());
        if (userOptional.isEmpty() || !userService.checkPassword(userDTO.getPassword(), userOptional.get().getPassword())) {
            return ResponseEntity.status(401).body("Identifiants invalides");
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(userOptional.get().getEmail())
                .password(userOptional.get().getPassword())
                .authorities("USER")
                .build();

        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(Map.of(
                "message", "Connexion réussie",
                "token", token
        ));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String email = jwtService.extractUsername(token);
        Optional<UserDTO> userOptional = userService.findByEmail(email);
        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(401).build());
    }
}
