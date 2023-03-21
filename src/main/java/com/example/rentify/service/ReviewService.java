package com.example.rentify.service;

import com.example.rentify.dto.ReviewApartmentDTO;
import com.example.rentify.dto.ReviewDTO;
import com.example.rentify.entity.Apartment;
import com.example.rentify.entity.Review;
import com.example.rentify.mapper.ReviewMapper;
import com.example.rentify.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
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

    @Cacheable(value = "reviews", key = "#id")
    public List<ReviewDTO> findByApartmentId(Integer id, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findReviewsByApartmentId(id, pageable);
        return reviews.hasContent() ?
                reviewMapper.toDTOList(reviews.getContent()) :
                Collections.emptyList();
    }

    @CachePut(value = "reviews", key = "#id")
    public Boolean delete(Integer id) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            review.setIsActive(false);
            reviewRepository.save(review);
            return true;
        } else return false;
    }

    @CachePut(value = "reviews", key = "#reviewApartmentDTO.id")
    public Boolean update(Integer id, ReviewApartmentDTO reviewApartmentDTO) {
        boolean reviewExists = reviewRepository.existsById(id);
        if (reviewExists) {
            reviewApartmentDTO.setId(id);
            reviewApartmentDTO.setIsActive(true);
            save(reviewApartmentDTO);
            return true;
        } else return false;
    }
}