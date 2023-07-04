package com.example.rentify.service;

import com.example.rentify.dto.CityDTO;
import com.example.rentify.dto.CityWithCountryDTO;
import com.example.rentify.entity.City;
import com.example.rentify.mapper.CityMapper;
import com.example.rentify.mapper.CityWithCountryMapper;
import com.example.rentify.repository.CityRepository;
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
public class CityService {

    private final CityMapper cityMapper;
    private final CityRepository cityRepository;
    private final CityWithCountryMapper cityWithCountryMapper;

    @Cacheable(value = "cityCountry", key = "{#name, #pageable.toString()}")
    public List<CityWithCountryDTO> findByName(String name, Pageable pageable) {
        log.info("Cache miss..Getting data from database.");
        Page<Integer> citiesIdsPage = cityRepository.findIdsPageable(pageable, name);
        List<City> cities = cityRepository.findByNameStartingWith(citiesIdsPage.getContent());
        return cityWithCountryMapper.toDTOList(cities);
    }

    @Cacheable(value = "cities", key = "{#countryName , #cityName, #pageable.toString()}")
    public List<CityDTO> findByCountryCityName(String countryName, String cityName, Pageable pageable) {
        log.info("Cache miss..Getting data from database.");
        Page<City> cities = cityRepository.findAllCitiesFromCountryCodeJPQL(countryName, cityName, pageable);
        return cities.hasContent() ? cityMapper.toDTOList(cities.getContent()) : Collections.emptyList();
    }
}