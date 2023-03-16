package com.example.rentify.controller;

import com.example.rentify.dto.CityDTO;
import com.example.rentify.service.CityService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/city")
public class CityController {

    private final CityService cityService;
    private final Logger LOGGER = LoggerFactory.getLogger(CityController.class);

    //GET http://localhost:8080/api/city/by-country-name?name=Montenegro
    @GetMapping("by-country-name")
    ResponseEntity<List<CityDTO>> findAllForCountryName(@RequestParam("name") String name) {
        List<CityDTO> cities = cityService.findByCountryName(name);
        LOGGER.info("Cities : {} ", cities);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    //GET http://localhost:8080/api/city/by-country-code?code=ME
    @GetMapping("by-country-code")
    ResponseEntity<List<CityDTO>> findAllForCountryCode(@RequestParam("code") String code) {
        List<CityDTO> cities = cityService.findByCountryCode(code);
        LOGGER.info("Cities : {} ", cities);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    //GET http://localhost:8080/api/city?name=B
    @GetMapping
    ResponseEntity<List<CityDTO>> findByName(@RequestParam("name") String name) {
        List<CityDTO> cities = cityService.findByName(name);
        LOGGER.info("Cities : {} ", cities);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    //insert city

    //update city

    //delete city
}
