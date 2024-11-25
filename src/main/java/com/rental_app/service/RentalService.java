package com.rental_app.service;

import com.rental_app.dto.RentalDTO;
import com.rental_app.model.Rental;
import com.rental_app.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    // Récupérer tous les rentals sous forme de DTO
    public List<RentalDTO> getAllRentals() {
        return rentalRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Récupérer un rental par ID sous forme de DTO
    public Optional<RentalDTO> getRentalById(Long id) {
        return rentalRepository.findById(id).map(this::convertToDTO);
    }

    // Créer un rental depuis un DTO
    public RentalDTO createRental(RentalDTO rentalDTO) {
        Rental rental = convertToEntity(rentalDTO);
        rental.setCreatedAt(LocalDateTime.now());
        rental.setUpdatedAt(LocalDateTime.now());
        Rental savedRental = rentalRepository.save(rental);
        return convertToDTO(savedRental);
    }

    // Mettre à jour un rental existant
    public Optional<RentalDTO> updateRental(Long id, RentalDTO rentalDTO) {
        return rentalRepository.findById(id).map(existingRental -> {
            existingRental.setName(rentalDTO.getName());
            existingRental.setSurface(rentalDTO.getSurface());
            existingRental.setPrice(rentalDTO.getPrice());
            existingRental.setPicture(rentalDTO.getPicture());
            existingRental.setDescription(rentalDTO.getDescription());
            existingRental.setUpdatedAt(LocalDateTime.now());
            Rental updatedRental = rentalRepository.save(existingRental);
            return convertToDTO(updatedRental);
        });
    }

    // Supprimer un rental
    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }

    // Convertir une entité Rental en DTO
    private RentalDTO convertToDTO(Rental rental) {
        return new RentalDTO(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                rental.getPicture(),
                rental.getDescription(),
                rental.getOwnerId(),
                rental.getCreatedAt(),
                rental.getUpdatedAt()
        );
    }

    // Convertir un DTO en entité Rental
    private Rental convertToEntity(RentalDTO rentalDTO) {
        Rental rental = new Rental();
        rental.setId(rentalDTO.getId());
        rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setPicture(rentalDTO.getPicture());
        rental.setDescription(rentalDTO.getDescription());
        rental.setOwnerId(rentalDTO.getOwnerId());
        rental.setCreatedAt(rentalDTO.getCreatedAt());
        rental.setUpdatedAt(rentalDTO.getUpdatedAt());
        return rental;
    }
}
