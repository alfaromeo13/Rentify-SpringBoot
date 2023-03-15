package com.example.rentify.mapper;

import com.example.rentify.dto.CountryDTO;
import com.example.rentify.entity.Country;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CountryMapper {
    Country toEntity(CountryDTO countryDTO);
}
