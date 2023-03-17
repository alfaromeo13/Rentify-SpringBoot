package com.example.rentify.controller;

import com.example.rentify.dto.CityDTO;
import com.example.rentify.dto.CountryDTO;
import com.example.rentify.service.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/country")
public class CountryController {

    private final CountryService countryService;

    @GetMapping("by-country-code") //GET http://localhost:8080/api/country/by-country-code?code=ME
    public ResponseEntity<List<CountryDTO>> findByCode(@RequestParam("code") String code) {
        List<CountryDTO> countries = countryService.findByShortCode(code);
        log.info("Cities : {} ", countries);
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping("by-country-name") //GET http://localhost:8080/api/country/by-country-name?name=Mon
    public ResponseEntity<List<CountryDTO>> findByNameStartingWith(@RequestParam("name") String name) {
        List<CountryDTO> countries = countryService.findByNameStartingWith(name);
        log.info("Cities : {} ", countries);
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }
}