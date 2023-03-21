package com.example.rentify.repository;

import com.example.rentify.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    @Query(value = "select country from Country country where country.name like concat(:name, '%')")
    List<Country> findByNameStartingWith(@Param("name") String name);

    @Query(value = "select country from Country country where country.shortCode like concat(:code, '%')")
    List<Country> findByShortCode(@Param("code") String shortCode);
}