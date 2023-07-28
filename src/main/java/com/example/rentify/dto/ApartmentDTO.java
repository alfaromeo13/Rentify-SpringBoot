package com.example.rentify.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "dd/MM/yyyy") //ovo parsira bajte i vrace u jsonu kao odgovor datum u ovom formatu
    private Date createdAt;
    private Boolean liked;
    private String number;
    private Boolean isActive;
    private Boolean isApproved;
    private Integer numberOfStars;

    private UserDTO user; //owner
    private PeriodDTO period;
    private AddressDTO address;
    private Set<ImageDTO> images;
    private PropertyTypeDTO propertyType;
    private Integer grade;
    private Set<ApartmentAttributeDTO> apartmentAttributes;
}