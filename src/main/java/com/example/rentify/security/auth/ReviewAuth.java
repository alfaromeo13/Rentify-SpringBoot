package com.example.rentify.security.auth;

import com.example.rentify.entity.Review;
import com.example.rentify.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewAuth { // reviewAuth <- name of the Bean

    private final ReviewRepository reviewRepository;

    public boolean hasPermission(Integer reviewId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Review review = reviewRepository.findReviewWithUser(reviewId);
        if (review == null || !review.getIsActive()) return false;
        return review.getUser().getUsername().equals(username);
    }
}