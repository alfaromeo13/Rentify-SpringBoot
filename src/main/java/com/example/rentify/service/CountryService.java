package com.example.rentify.service;

import com.example.rentify.dto.CountryDTO;
import com.example.rentify.entity.Country;
import com.example.rentify.mapper.CountryMapper;
import com.example.rentify.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryMapper countryMapper;
    private final CountryRepository countryRepository;

    @Cacheable(value = "country", key = "#name")
    public List<CountryDTO> findByNameStartingWith(String name) {
        log.info("Cache miss..Getting data from database.");
        List<Country> countries = countryRepository.findByNameStartingWith(name);
        return countryMapper.toDTOList(countries);
    }

    @Cacheable(value = "country", key = "#shortCode")
    public List<CountryDTO> findByShortCode(String shortCode) {
        log.info("Cache miss..Getting data from database.");
        List<Country> countries = countryRepository.findByShortCode(shortCode);
        return countryMapper.toDTOList(countries);
    }
}