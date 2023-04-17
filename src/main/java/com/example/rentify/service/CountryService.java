package com.example.rentify.service;

import com.example.rentify.dto.CountryDTO;
import com.example.rentify.entity.Country;
import com.example.rentify.mapper.CountryMapper;
import com.example.rentify.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryMapper countryMapper;
    private final CountryRepository countryRepository;

    @Cacheable(value = "country", key = "{#name, #pageable.toString()}")
    public List<CountryDTO> findByNameStartingWith(String name, Pageable pageable) {
        log.info("Cache miss..Getting data from database.");
        Page<Country> countries = countryRepository.findByNameStartingWith(name, pageable);
        return countries.hasContent() ? countryMapper.toDTOList(countries.getContent()) : Collections.emptyList();
    }

    @Cacheable(value = "country", key = "{#shortCode,#pageable.toString()}")
    public List<CountryDTO> findByShortCode(String shortCode, Pageable pageable) {
        log.info("Cache miss..Getting data from database.");
        Page<Country> countries = countryRepository.findByShortCode(shortCode, pageable);
        return countries.hasContent() ? countryMapper.toDTOList(countries.getContent()) : Collections.emptyList();
    }
}