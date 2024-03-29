package com.example.rentify.repository;

import com.example.rentify.entity.ApartmentAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentAttributeRepository extends JpaRepository<ApartmentAttribute, Integer> {
}
