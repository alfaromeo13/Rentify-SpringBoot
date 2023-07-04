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

    @GetMapping //GET http://localhost:8080/api/city?page=0&size=4&name=B
    public ResponseEntity<List<CityWithCountryDTO>> findByName(Pageable pageable, @RequestParam String name) {
        List<CityWithCountryDTO> cities = cityService.findByName(name, pageable);
        log.info("Cities : {} ", cities);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    //GET http://localhost:8080/api/city/by-country-code?page=0&size=5&countryCode=Montenegro&cityName=P
    @GetMapping("by-country-code")
    public ResponseEntity<List<CityDTO>> findForCountryAndCityName(
            @RequestParam String countryCode, @RequestParam String cityName, Pageable pageable) {
        List<CityDTO> cities = cityService.findByCountryCityName(countryCode, cityName, pageable);
        log.info("Cities : {} ", cities);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }
}