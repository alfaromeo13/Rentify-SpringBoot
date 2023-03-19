package com.example.rentify.service;

import com.example.rentify.dto.ReviewDTO;
import com.example.rentify.entity.Review;
import com.example.rentify.mapper.ReviewMapper;
import com.example.rentify.mapper.RoleMapper;
import com.example.rentify.repository.ReviewRepository;
import com.example.rentify.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper reviewMapper;
    private final ReviewRepository reviewRepository;

    public void save(ReviewDTO reviewDTO) {
        reviewRepository.save(reviewMapper.toEntity(reviewDTO));
    }

    public Boolean delete(Integer id) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            review.setIsActive(false);
            reviewRepository.save(review);
            return true;
        } else return false;
    }

    public Boolean update(Integer id, ReviewDTO reviewDTO) {
        boolean reviewExists = reviewRepository.existsById(id);
        if (reviewExists) {
            reviewDTO.setId(id);
            save(reviewDTO);
            return true;
        } else return false;
    }
}