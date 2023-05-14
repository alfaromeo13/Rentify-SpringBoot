package com.example.rentify.search;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ApartmentSearch {
    private Integer id;
    private String type;
    private String title;
    private String period;
    private Double minPrice;
    private Double maxPrice;
    private Integer minNumOfBedrooms;
    private Integer maxNumOfBedrooms;
    private Integer minNumOfBathrooms;
    private Integer maxNumOfBathrooms;
    private Integer minSquareMeters;
    private Integer maxSquareMeters;
    private String countryName;
    private String cityName;
    private String neighborhoodName;
    private Integer userId;//When user logs in he wants a list of apartments he owns

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date availableFrom, availableTo;// filters available apartments for that period

    //attributes:
    private String parking;
    private String petsAllowed;
    private String balcony;
    private String pool;
    private String airConditioning;
    private String furnished;
    private String wiFi;
    private String elevator;
    private String publicTransportDist; // filter apartments by their proximity to public transportation
    private String appliances; // common household appliances that are commonly found in kitchens
}