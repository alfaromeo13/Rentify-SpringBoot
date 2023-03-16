package com.example.rentify.repository;

import com.example.rentify.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    List<City> findByNameStartingWith(String name);

    @Query(value = "select city from City city" +
            " join city.country country where country.name = :name")
    List<City> findAllCitiesFromCountryNameJPQL(@Param("name") String countryName);

    //ne treba nam 'join on' Samo nam treba 'join' jer smo sami join definisali
    // u City entitetu na  polju country
    @Query(value = "select city from City city " +
            "join city.country country where country.shortCode = :shortCode")
    List<City> findAllCitiesFromCountryCodeJPQL(@Param("shortCode") String code);


//    @Query(value = "select city from City as city join fetch city.country")
//    List<City> findAllWithCountries();
}