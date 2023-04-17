package com.example.rentify.repository;

import com.example.rentify.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    @Query(value = "select country from Country country where country.name like concat(:name, '%')")
    Page<Country> findByNameStartingWith(@Param("name") String name, Pageable pageable);

    @Query(value = "select country from Country country where country.shortCode like concat(:code, '%')")
    Page<Country> findByShortCode(@Param("code") String shortCode, Pageable pageable);
}