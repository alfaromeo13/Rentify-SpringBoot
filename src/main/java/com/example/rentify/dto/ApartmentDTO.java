package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class ApartmentDTO implements Serializable {
    private Integer id;
    private String title;
    private Double price;
    private String description;
    private Integer sqMeters;
    private Integer numOfBedrooms;
    private Integer numOfBathrooms;
    private Date createdAt;
    private String number;
    private Boolean isActive;

    private UserDTO user; //owner
    private PeriodDTO period;
    private AddressDTO address;
    private Set<ImageDTO> images;
    private PropertyTypeDTO propertyType;
    private Set<ApartmentAttributeDTO> apartmentAttributes;
}