package com.example.rentify.mapper;

import com.example.rentify.dto.CountryDTO;
import com.example.rentify.entity.Country;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    Country toEntity(CountryDTO countryDTO);
    List<CountryDTO> toDTOList(List<Country> country);
}
