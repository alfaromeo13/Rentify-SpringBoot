package com.example.rentify.security.auth;

import com.example.rentify.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageAuth {
    private final ImageRepository imageRepository;

    public boolean hasPermission(Integer imageId) { //checking if we are the owner of that image
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return imageRepository.existsByIdAndApartmentUserUsername(imageId, username);
    }
}