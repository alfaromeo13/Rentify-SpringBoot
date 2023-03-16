package com.example.rentify.service;

import com.example.rentify.dto.CityDTO;
import com.example.rentify.entity.City;
import com.example.rentify.mapper.CityMapper;
import com.example.rentify.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityMapper cityMapper;
    private final CityRepository cityRepository;

    public List<CityDTO> findByCountryName(String name) {
        List<City> cities = cityRepository.findAllCitiesFromCountryNameJPQL(name);
        return cityMapper.toDTOList(cities);
    }

    public List<CityDTO> findByCountryCode(String code) {
        List<City> cities = cityRepository.findAllCitiesFromCountryCodeJPQL(code);
        return cityMapper.toDTOList(cities);
    }

    public List<CityDTO> findByName(String name) {
        List<City> cities = cityRepository.findByNameStartingWith(name);
        return cityMapper.toDTOList(cities);
    }
}