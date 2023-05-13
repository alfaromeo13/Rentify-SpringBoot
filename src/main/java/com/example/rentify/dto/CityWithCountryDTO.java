package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class CityWithCountryDTO extends CityDTO {
    private CountryDTO country;
}