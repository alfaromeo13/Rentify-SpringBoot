package com.example.rentify.repository;

import com.example.rentify.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    @Query(value = "select city from City city where city.name like concat(:name , '%')")
    Page<City> findByNameStartingWith(@Param("name") String name, Pageable pageable);

    @Query(value = "select city from City city join city.country country where country.name like concat(:name , '%')")
    Page<City> findAllCitiesFromCountryNameJPQL(@Param("name") String countryName, Pageable pageable);

    @Query(value = "select city from City city join city.country country where country.shortCode like concat(:code , '%')")
    Page<City> findAllCitiesFromCountryCodeJPQL(@Param("code") String shortCode, Pageable pageable);
}