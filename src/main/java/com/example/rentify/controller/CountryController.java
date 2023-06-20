package com.example.rentify.controller;

import com.example.rentify.dto.CountryDTO;
import com.example.rentify.service.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
    //ove 2 metode cemo iskoristiti za sekciju filtriranja u samom sajtu

    @GetMapping("by-country-code") //GET http://localhost:8080/api/country/by-country-code?page=0&size=5&code=ME
    public ResponseEntity<List<CountryDTO>> findByCode(@RequestParam("code") String code, Pageable pageable) {
        List<CountryDTO> countries = countryService.findByShortCode(code, pageable);
        log.info("Countries : {} ", countries);
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping("by-country-name") //GET http://localhost:8080/api/country/by-country-name?page=0&size=5&name=Mon
    public ResponseEntity<List<CountryDTO>> findByNameStartingWith(
            @RequestParam("name") String name, Pageable pageable) {
        List<CountryDTO> countries = countryService.findByNameStartingWith(name, pageable);
        log.info("Countries : {} ", countries);
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }
}