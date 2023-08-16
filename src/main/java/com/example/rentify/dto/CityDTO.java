package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;

@Data
public class CityDTO implements Serializable {
    private String name;
    private CountryDTO country;
    private static final long serialVersionUID = 1962547045016594822L;
}
