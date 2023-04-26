package com.example.rentify.security.auth;

import com.example.rentify.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RentalAuth {
    private final RentalRepository rentalRepository;

    public boolean hasPermission(Integer rentalId) {
        //checking if we are the owner of that apartment
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String ownerUsername = rentalRepository.ownerUsername(rentalId);
        if (ownerUsername == null) return false;
        return ownerUsername.equals(username);
    }
}