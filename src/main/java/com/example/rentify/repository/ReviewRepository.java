package com.example.rentify.repository;

import com.example.rentify.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query(value = "select review from Review review join review.apartment apartment where apartment.id= :id")
    Page<Review> findReviewsByApartmentId(@Param("id") Integer id, Pageable pageable);
}
