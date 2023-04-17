package com.example.rentify.mapper;

import com.example.rentify.dto.CityWithCountryDTO;
import com.example.rentify.entity.City;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityWithCountryMapper {
    List<CityWithCountryDTO> toDTOList(List<City> cities);
}
