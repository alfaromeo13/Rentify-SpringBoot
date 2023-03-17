package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentDTO implements Serializable {
    private Integer id;
    private String title;
    private String description;
    private Integer sqMeters;
    private Double price;
    private Integer numOfBedrooms;
    private Integer numOfBathrooms;
    private Date createdAt;
    private String number;
    private Boolean isAvailable;
    private Boolean isActive;

    private UserDTO user; //owner
    private AddressDTO address;
    private List<ReviewDTO> reviews;
    private List<VideoDTO> videos;
    private List<ImageDTO> images;
    private List<ApartmentAttributeDTO> apartmentAttributes;
}