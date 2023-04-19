package com.example.rentify.service;

import com.example.rentify.dto.ReviewApartmentDTO;
import com.example.rentify.dto.ReviewDTO;
import com.example.rentify.entity.Review;
import com.example.rentify.entity.User;
import com.example.rentify.mapper.ReviewMapper;
import com.example.rentify.mapper.UserMapper;
import com.example.rentify.repository.ApartmentRepository;
import com.example.rentify.repository.ReviewRepository;
import com.example.rentify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@CacheEvict(value = "reviews", allEntries = true)
public class ReviewService {
    private final UserMapper userMapper;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ApartmentRepository apartmentRepository;

    public void save(ReviewApartmentDTO reviewApartmentDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        reviewApartmentDTO.setUser(userMapper.toDTO(user));
        Review review = reviewMapper.toEntity(reviewApartmentDTO);
        review.setApartment(apartmentRepository.getById(reviewApartmentDTO.getApartmentId()));
        reviewRepository.save(review);
    }

    public void update(Integer id, ReviewApartmentDTO reviewApartmentDTO) {
        reviewApartmentDTO.setId(id);
        reviewApartmentDTO.setIsActive(true);
        save(reviewApartmentDTO);
    }

    public void delete(Integer id) {
        Review review = reviewRepository.getById(id);
        review.setIsActive(false);
        reviewRepository.save(review);
    }

    //with # we read param value and we cache it into RAM
    @Cacheable(value = "reviews", key = "{#id,#pageable.toString()}")
    public List<ReviewDTO> findByApartmentId(Integer id, Pageable pageable) {
        log.info("Cache miss..Getting data from database.");
        Page<Review> reviewsPage = reviewRepository.findReviewsByApartmentId(id, pageable);
        return reviewsPage.hasContent() ? reviewMapper.toDTOList(reviewsPage.getContent()) : Collections.emptyList();
    }
}