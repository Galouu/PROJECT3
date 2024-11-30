package com.rental_app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rental_app.dto.RentalDTO;
import com.rental_app.model.Rental;
import com.rental_app.repository.RentalRepository;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public List<RentalDTO> getAllRentals() {
        return rentalRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<RentalDTO> getRentalById(Long id) {
        return rentalRepository.findById(id).map(this::convertToDTO);
    }

    public RentalDTO createRental(RentalDTO rentalDTO) {
        Rental rental = convertToEntity(rentalDTO);
        rental.setCreatedAt(LocalDateTime.now());
        rental.setUpdatedAt(LocalDateTime.now());
        Rental savedRental = rentalRepository.save(rental);
        return convertToDTO(savedRental);
    }

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

    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }

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
