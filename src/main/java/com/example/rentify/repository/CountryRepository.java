package com.example.rentify.repository;

import com.example.rentify.entity.Country;
import com.example.rentify.projections.CountryIdAndShortCodeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    //metoda koja vrace sve drzave iz nase tabele i svaki red ce se mapirati u klasu country
    @Query(value = "select country from Country as country")
    List<Country> findAllCountries();

    //ako nam treba samo odredjeni set podataka npr id i short code pisemo interfejs
    @Query(value = "select country.id as id ,country.shortCode as shortCode from Country as country")
    List<CountryIdAndShortCodeProjection> findIdAndCodeUsingCustomProjection();
}