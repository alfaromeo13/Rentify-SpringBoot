package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;

@Data
public class CountryDTO implements Serializable {
    private Integer id;
    private String name;
    private String shortCode;
}