package com.example.rentify.repository;

import com.example.rentify.entity.Apartment;
import com.example.rentify.projections.AdminApartmentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Integer>, JpaSpecificationExecutor<Apartment> {
    boolean existsByIdAndIsApprovedTrueAndUserUsername(Integer id, String username);

    @Query("SELECT " +
            "SUM(CASE WHEN type.name = 'Apartment' THEN 1 ELSE 0 END) AS apartments, " +
            "SUM(CASE WHEN type.name = 'Condo' THEN 1 ELSE 0 END) AS condos, " +
            "SUM(CASE WHEN type.name = 'House' THEN 1 ELSE 0 END) AS houses, " +
            "SUM(CASE WHEN type.name = 'Studio' THEN 1 ELSE 0 END) AS studios " +
            "FROM Apartment apartment " +
            "JOIN apartment.propertyType type where apartment.isApproved = true")
    AdminApartmentProjection getProjection();
}