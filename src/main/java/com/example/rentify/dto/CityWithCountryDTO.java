package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CityWithCountryDTO implements Serializable {
    private String name;
    private CountryDTO country;
}
