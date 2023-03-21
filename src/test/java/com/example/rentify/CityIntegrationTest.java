package com.example.rentify;

import com.example.rentify.entity.City;
import com.example.rentify.entity.Country;
import com.example.rentify.repository.CityRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest(classes = RentifyApplication.class)
public class CityIntegrationTest {

    @Autowired
    private CityRepository cityRepository;

//    @Test
//    public void getAllCitiesFromGivenCountryTest() {
//        List<City> cities = cityRepository.findAllCitiesFromCountryCodeJPQL("ME");
//        log.info("{}", cities);
//    }
//
//    @Test
//    public void getCityWithCountries() {
//        List<City> cities = cityRepository.findByNameStartingWith("Mon");
//        log.info("{}", cities);
//    }

    @Test
    public void insertCityForCountryTest() {
        Country country = new Country();
        country.setId(1);
        City city = new City();
        city.setName("Konik city");
        city.setCountry(country);
        cityRepository.save(city);
    }
}
