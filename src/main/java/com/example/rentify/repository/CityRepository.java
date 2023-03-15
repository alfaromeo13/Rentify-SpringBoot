package com.example.rentify.repository;

import com.example.rentify.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    //ne treba nam 'join on' samo join jer smo to vec definisali ranije u Country entitetu na samom polju
    //country
    @Query(value = "select city from City as city " +
            "join fetch city.country as country " + // sa fetch dovlacimo i country nazad
            "where country.shortCode = :shortCode")
    List<City> getAllCitiesFromCountryJPQL(@Param("shortCode") String code);

    @Query(value = "select city from City as city join fetch city.country")
    List<City> findAllWithCountries();
}