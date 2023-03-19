package com.example.rentify.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentSearch {
    private Integer id;//
    private String title;//
    private String description;//
    private Double minPrice;//
    private Double maxPrice;//
    private Integer minNumOfBedrooms;//
    private Integer maxNumOfBedrooms;//
    private Integer minNumOfBathrooms;//
    private Integer maxNumOfBathrooms;//
    private Integer minSquareMeters;//
    private Integer maxSquareMeters;//
    private String addressStreet;//
    private String neighborhoodName;//search for apartments by specific neighborhoods //
    private String cityName;//
    private String countryName; //
    private String availabilityDate;
    private Integer userId;//
    //attributes:
    private Boolean parking;
    private Boolean petsAllowed;
    private Boolean balcony;
    private Boolean airConditioning;
    private Boolean furnished;
    private Boolean wiFi;
    private Boolean elevator;
    private Integer distanceToPublicTransport;//Allow users to filter apartments by their proximity to public transportation options, such as bus stops or subway stations.
    private Boolean appliances; //" These are all common household appliances that are commonly found in kitchens, and are used for cooking, baking, and storing food.
}