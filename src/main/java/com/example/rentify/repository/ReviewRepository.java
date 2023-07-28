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
    @Query(value = "select avg(review.grade) from Review review " +
            "join review.apartment apartment where apartment.id= :id and " +
            "review.isActive = true and review.user.isActive = true")
    Double findNumberOfStars(@Param("id") Integer id);

    @Query(value = "select review from Review review " +
            "join review.apartment apartment where apartment.id= :id and " +
            "review.isActive = true and review.user.isActive = true")
    Page<Review> findReviewsByApartmentId(@Param("id") Integer id, Pageable pageable);

    boolean existsByIdAndIsActiveTrueAndUserUsername(Integer id,String username);
}