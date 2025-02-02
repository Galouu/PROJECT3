package com.rental_app.controller;

import com.rental_app.dto.RentalDTO;
import com.rental_app.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Operation(summary = "Récupérer tous les rentals", description = "Cette route retourne la liste complète des rentals disponibles.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RentalDTO.class))})
    })
    @GetMapping
    public ResponseEntity<List<RentalDTO>> getAllRentals() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    @Operation(summary = "Récupérer un rental par ID", description = "Cette route retourne un rental spécifique selon son ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rental trouvé",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RentalDTO.class))}),
        @ApiResponse(responseCode = "404", description = "Rental non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable Long id) {
        return rentalService.getRentalById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Créer un rental", description = "Cette route permet de créer un nouveau rental.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Rental créé avec succès",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RentalDTO.class))}),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping
    public ResponseEntity<RentalDTO> createRental(@RequestBody RentalDTO rentalDTO) {
        RentalDTO createdRental = rentalService.createRental(rentalDTO);
        return ResponseEntity.status(201).body(createdRental);
    }

    @Operation(summary = "Mettre à jour un rental", description = "Cette route met à jour les informations d'un rental existant selon son ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rental mis à jour avec succès",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RentalDTO.class))}),
        @ApiResponse(responseCode = "404", description = "Rental non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RentalDTO> updateRental(@PathVariable Long id, @RequestBody RentalDTO rentalDTO) {
        return rentalService.updateRental(id, rentalDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Supprimer un rental", description = "Cette route permet de supprimer un rental existant par ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Rental supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Rental non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/upload-picture")
    public ResponseEntity<String> uploadPicture(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
    try {
        rentalService.savePicture(id, file);
        return ResponseEntity.ok("Picture uploaded successfully!");
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Error uploading picture: " + e.getMessage());
    }
    }
}
