package com.rental_app.service;

import com.rental_app.model.Rental;
import com.rental_app.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    //  GET ALL THE RENTAL
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    // GET THE RENTAL BY ID
    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    // CREATE A NEW RENTAL
    public Rental createRental(Rental rental) {
        rental.setCreatedAt(LocalDateTime.now());
        rental.setUpdatedAt(LocalDateTime.now());
        return rentalRepository.save(rental);
    }

    // DELETE A RENTAL
    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }

    // UPDATE A EXISTING RENTAL WITH THE ID
    public Optional<Rental> updateRental(Long id, Rental rentalDetails) {
        return rentalRepository.findById(id).map(existingRental -> {
            existingRental.setName(rentalDetails.getName());
            existingRental.setSurface(rentalDetails.getSurface());
            existingRental.setPrice(rentalDetails.getPrice());
            existingRental.setPicture(rentalDetails.getPicture());
            existingRental.setDescription(rentalDetails.getDescription());
            existingRental.setUpdatedAt(LocalDateTime.now());
            return rentalRepository.save(existingRental);
        });
    }
}
