package com.example.rentify.controller;

import com.example.rentify.dto.CityDTO;
import com.example.rentify.dto.CityWithCountryDTO;
import com.example.rentify.service.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping //GET http://localhost:8080/api/city?name=B
    public ResponseEntity<List<CityWithCountryDTO>> findByName(@RequestParam("name") String name) {
        List<CityWithCountryDTO> cities = cityService.findByName(name);
        log.info("Cities : {} ", cities);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @GetMapping("by-country-name") //GET http://localhost:8080/api/city/by-country-name?name=Montenegro
    public ResponseEntity<List<CityDTO>> findAllForCountryName(@RequestParam("name") String name) {
        List<CityDTO> cities = cityService.findByCountryName(name);
        log.info("Cities : {} ", cities);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @GetMapping("by-country-code") //GET http://localhost:8080/api/city/by-country-code?code=ME
    public ResponseEntity<List<CityDTO>> findAllForCountryCode(@RequestParam("code") String code) {
        List<CityDTO> cities = cityService.findByCountryCode(code);
        log.info("Cities : {} ", cities);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }
}