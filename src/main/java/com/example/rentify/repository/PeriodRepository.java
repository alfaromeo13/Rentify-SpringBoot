package com.example.rentify.repository;

import com.example.rentify.entity.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodRepository extends JpaRepository<Period, String> {
    boolean existsByName(String name);
}
