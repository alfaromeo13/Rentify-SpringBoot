package com.example.rentify.service;

import com.example.rentify.dto.NotificationDTO;
import com.example.rentify.dto.ReviewApartmentDTO;
import com.example.rentify.dto.ReviewDTO;
import com.example.rentify.entity.Apartment;
import com.example.rentify.entity.Review;
import com.example.rentify.entity.User;
import com.example.rentify.mapper.ReviewMapper;
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
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final NotificationService notificationService;
    private final ApartmentRepository apartmentRepository;

    public void save(ReviewApartmentDTO reviewApartmentDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        Review review = reviewMapper.toEntity(reviewApartmentDTO);
        Apartment apartment = apartmentRepository.getById(reviewApartmentDTO.getApartmentId());
        review.setApartment(apartment);
        review.setUser(user);
        reviewRepository.save(review);
        NotificationDTO notification=new NotificationDTO();
        notification.setReceiverUsername(apartment.getUser().getUsername());
        notification.setMessage(" gave you "+review.getGrade()+"/5 stars for your "
                + apartment.getPropertyType().getName().toLowerCase()+" in"
                +" "+apartment.getAddress().getStreet()+" "+apartment.getAddress().getNeighborhood().getName()
                +" ,"+apartment.getAddress().getNeighborhood().getCity().getName());
        notificationService.save(notification);
    }

    public void delete(Integer id) {
        Review review = reviewRepository.getById(id);
        review.setIsActive(false);
        reviewRepository.save(review);
    }

    public Double numberOfStars(Integer id){
        return reviewRepository.findNumberOfStars(id);
    }

    //with # we read param value and we cache it into RAM
    @Cacheable(value = "reviews", key = "{#id,#pageable.toString()}")
    public List<ReviewDTO> findByApartmentId(Integer id, Pageable pageable) {
        log.info("Cache miss..Getting data from database.");
        Page<Review> reviewsPage = reviewRepository.findReviewsByApartmentId(id, pageable);
        return reviewsPage.hasContent() ? reviewMapper.toDTOList(reviewsPage.getContent()) : Collections.emptyList();
    }
}