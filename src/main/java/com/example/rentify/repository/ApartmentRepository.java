package com.example.rentify.repository;

import com.example.rentify.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Integer>, JpaSpecificationExecutor<Apartment> {
    boolean existsByIdAndUserUsername(Integer id, String username);
}