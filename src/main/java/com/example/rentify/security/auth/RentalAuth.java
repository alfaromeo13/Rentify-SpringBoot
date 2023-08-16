package com.example.rentify.security.auth;

import com.example.rentify.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RentalAuth {

    private final RentalRepository rentalRepository;

    public boolean hasPermission(Integer rentalId) { //checking if we are the owner of apartment
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return rentalRepository.existsByIdAndApartmentUserUsername(rentalId, username);
    }
}