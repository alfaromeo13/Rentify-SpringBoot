package com.example.rentify.search;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ApartmentSearch {
    private Double minPrice;
    private Double maxPrice;
    private Integer minNumOfBedrooms;
    private Integer maxNumOfBedrooms;
    private Integer minNumOfBathrooms;
    private Integer maxNumOfBathrooms;
    private Integer minSquareMeters;
    private Integer maxSquareMeters;
    private String countryName;
    private String countryCode;
    private String cityName;
    private String neighborhoodName;
    private Integer userId;//When user logs in he wants a list of apartments he owns
    private List<Integer> id=new ArrayList<>();
    private List<String> type=new ArrayList<>();
    private List<String> period=new ArrayList<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date availableFrom, availableTo;// filters available apartments for that period

    //attributes:
    private String parking;
    private String petsAllowed;
    private String balcony;
    private String airConditioning;
    private String furnished;
    private String wiFi;
    private String elevator;
    private String appliances; // common household appliances that are commonly found in kitchens
}