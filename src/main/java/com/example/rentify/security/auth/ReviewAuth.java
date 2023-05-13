package com.example.rentify.security.auth;

import com.example.rentify.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewAuth { // reviewAuth <- name of the Bean

    private final ReviewRepository reviewRepository;

    public boolean hasPermission(Integer reviewId) { //checking if we wrote the comment
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return reviewRepository.existsByIdAndIsActiveTrueAndUserUsername(reviewId, username);
    }
}