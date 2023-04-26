package com.example.rentify.repository;

import com.example.rentify.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Integer>, JpaSpecificationExecutor<Apartment> {
    @Query(value = "select ap from Apartment ap join fetch ap.user user where ap.id = :id")
    Apartment findApartmentByIdWithUser(@Param("id") Integer id);

    @Query(value = "select ap.price from Apartment ap where ap.id= :id")
    Double getPrice(@Param("id") Integer id);
}