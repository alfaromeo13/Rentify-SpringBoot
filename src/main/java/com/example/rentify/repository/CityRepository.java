package com.example.rentify.repository;

import com.example.rentify.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    @Query(value = "select city from City city join fetch city.country where city.name like :name%")
    List<City> findByNameStartingWith(@Param("name") String name);

    @Query(value = "select city from City city join city.country country where country.name like :name%")
    List<City> findAllCitiesFromCountryNameJPQL(@Param("name") String countryName);

    @Query(value = "select city from City city join city.country country where country.shortCode like :code%")
    List<City> findAllCitiesFromCountryCodeJPQL(@Param("code") String shortCode);
}