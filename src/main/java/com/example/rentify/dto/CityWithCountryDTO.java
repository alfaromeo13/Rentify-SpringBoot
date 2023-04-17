package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CityWithCountryDTO extends CityDTO implements Serializable {
    private CountryDTO country;
}