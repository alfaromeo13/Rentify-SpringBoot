package com.example.rentify.mapper;

import com.example.rentify.dto.CityDTO;
import com.example.rentify.entity.City;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {
    City toEntity(CityDTO cityDTO);
    List<CityDTO> toDTOList(List<City> cityList);
}
