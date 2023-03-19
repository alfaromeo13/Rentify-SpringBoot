package com.example.rentify.service;

import com.example.rentify.dto.ReviewApartmentDTO;
import com.example.rentify.entity.Apartment;
import com.example.rentify.entity.Review;
import com.example.rentify.mapper.ReviewMapper;
import com.example.rentify.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper reviewMapper;
    private final ReviewRepository reviewRepository;

    public void save(ReviewApartmentDTO reviewApartmentDTO) {
        Review review = reviewMapper.toEntity(reviewApartmentDTO);
        Apartment apartment = new Apartment();
        apartment.setId(reviewApartmentDTO.getApartmentId());
        review.setApartment(apartment);
        reviewRepository.save(review);
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

    public Boolean update(Integer id, ReviewApartmentDTO reviewApartmentDTO) {
        boolean reviewExists = reviewRepository.existsById(id);
        if (reviewExists) {
            reviewApartmentDTO.setId(id);
            save(reviewApartmentDTO);
            return true;
        } else return false;
    }
}