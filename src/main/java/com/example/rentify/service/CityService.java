package com.example.rentify.service;

import com.example.rentify.dto.CityDTO;
import com.example.rentify.dto.CityWithCountryDTO;
import com.example.rentify.entity.City;
import com.example.rentify.mapper.CityMapper;
import com.example.rentify.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityService {

    private final CityMapper cityMapper;
    private final CityRepository cityRepository;

    @Cacheable(value = "cityCountry", key = "#name")
    public List<CityWithCountryDTO> findByName(String name) {
        log.info("Cache miss..Getting data from database.");
        List<City> cities = cityRepository.findByNameStartingWith(name);
        return cityMapper.toCityCountryDTOList(cities);
    }

    @Cacheable(value = "cities", key = "#name")
    public List<CityDTO> findByCountryName(String name) {
        log.info("Cache miss..Getting data from database.");
        List<City> cities = cityRepository.findAllCitiesFromCountryNameJPQL(name);
        return cityMapper.toDTOList(cities);
    }

    @Cacheable(value = "cities", key = "#code")
    public List<CityDTO> findByCountryCode(String code) {
        log.info("Cache miss..Getting data from database.");
        List<City> cities = cityRepository.findAllCitiesFromCountryCodeJPQL(code);
        return cityMapper.toDTOList(cities);
    }
}