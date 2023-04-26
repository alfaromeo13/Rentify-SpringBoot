package com.example.rentify.security.auth;

import com.example.rentify.entity.Apartment;
import com.example.rentify.repository.ApartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApartmentAuth {

    private final ApartmentRepository apartmentRepository;

    public boolean hasPermission(Integer apartmentId) {
        //checking if we are the owner of that apartment
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Apartment apartment = apartmentRepository.findApartmentByIdWithUser(apartmentId);
        if (apartment == null || !apartment.getIsActive()) return false;
        return apartment.getUser().getUsername().equals(username);
    }
}