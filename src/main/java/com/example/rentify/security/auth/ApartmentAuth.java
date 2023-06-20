package com.example.rentify.security.auth;

import com.example.rentify.repository.ApartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApartmentAuth {

    private final ApartmentRepository apartmentRepository;

    public boolean existsById(Integer apartmentId) {
        return apartmentRepository.existsById(apartmentId);
    }

    public boolean hasPermission(Integer apartmentId) { //checking if we are the owner of apartment
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return apartmentRepository.existsByIdAndUserUsername(apartmentId, username);
    }
}