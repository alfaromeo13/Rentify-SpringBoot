package com.example.rentify.controller;

import com.example.rentify.dto.CityDTO;
import com.example.rentify.dto.CityWithCountryDTO;
import com.example.rentify.service.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/city")
public class CityController {

    private final CityService cityService;

    //pozivamo na prvoj stanici sajta
    @GetMapping //GET http://localhost:8080/api/city?page=0&size=4&name=B
    public ResponseEntity<List<CityWithCountryDTO>> findByName(Pageable pageable, @RequestParam("name") String name) {
        List<CityWithCountryDTO> cities = cityService.findByName(name, pageable);
        log.info("Cities : {} ", cities);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    //kada u filtar sekciji izaberemo drzavu po izabranoj drzavi izlistaj gradove koje unesemo
    //GET http://localhost:8080/api/city/by-country-city-name?page=0&size=5&countryName=Montenegro&cityName=P
    @GetMapping("by-country-city-name")
    public ResponseEntity<List<CityDTO>> findForCountryAndCityName(
            @RequestParam("countryName") String countryName,
            @RequestParam("cityName") String cityName, Pageable pageable) {
        List<CityDTO> cities = cityService.findByCountryCityName(countryName, cityName, pageable);
        log.info("Cities : {} ", cities);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }
}