package com.example.rentify.repository;

import com.example.rentify.entity.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyTypeRepository extends JpaRepository<PropertyType, String> {
    boolean existsByName(String name);
}