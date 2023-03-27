package com.example.rentify.repository;

import com.example.rentify.entity.Rental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
    @Query(value = "select rental from Rental rental " +
            "join rental.apartment apartment where apartment.id= :id " +
            "order by rental.id desc")
    Page<Rental> findReviewsByApartmentId(@Param("id") Integer id, Pageable pageable);
}
