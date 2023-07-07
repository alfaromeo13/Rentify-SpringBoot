package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;

@Data
public class AddressDTO implements Serializable {
    private Double x;
    private Double y;
    private String street;
    private NeighborhoodDTO neighborhood;
}