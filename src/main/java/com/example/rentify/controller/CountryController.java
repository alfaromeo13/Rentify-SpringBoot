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

    @GetMapping("by-country") //GET http://localhost:8080/api/country/by-country?page=0&size=5&name=Mon
    public ResponseEntity<List<CountryDTO>> findByNameStartingWith(@RequestParam String name, Pageable pageable) {
        List<CountryDTO> countries = countryService.findByNameOrCodeStartingWith(name, pageable);
        log.info("Countries : {} ", countries);
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }
}