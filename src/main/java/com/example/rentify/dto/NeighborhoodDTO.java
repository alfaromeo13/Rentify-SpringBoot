package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;

@Data
public class NeighborhoodDTO implements Serializable {
    private Integer id;
    private String name;
    private CityDTO city;
    private static final long serialVersionUID = 81400279832606984L;
}
