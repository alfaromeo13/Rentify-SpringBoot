package com.example.rentify.service;

import com.example.rentify.dto.CountryDTO;
import com.example.rentify.entity.Country;
import com.example.rentify.mapper.CountryMapper;
import com.example.rentify.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public List<CountryDTO> findByNameStartingWith(String name) {
        List<Country> countries = countryRepository.findByNameStartingWith(name);
        return countryMapper.toDTOList(countries);
    }

    public List<CountryDTO> findByShortCode(String shortCode) {
        List<Country> countries = countryRepository.findByShortCode(shortCode);
        return countryMapper.toDTOList(countries);
    }
}