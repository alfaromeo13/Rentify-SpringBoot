package com.example.rentify.search;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ApartmentSearch {
    private Integer id;
    private String title;
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
    private Integer userId;//we get all apartments for specific user.When user logs in he wants a list of apartments he owns

    @DateTimeFormat(pattern = "dd-MM-yyyy") //<--ovo napravi da radi i tetiraj(vidji ovu metodu u Filter klasi)
    private Date availableFrom, availableTo;// filters available apartments for that period
    //ovo ces da uradis tako sto ce se u serch-u uvijek slati trenutni datum pa ce ti za taj datum izlistati
    //trenutno slobodne stanove

    //attributes:
    private String parking;
    private String petsAllowed;
    private String balcony;
    private String pool;
    private String airConditioning;
    private String furnished;
    private String wiFi;
    private String elevator;
    private String publicTransportDist; // Allow users to filter apartments by their proximity to public transportation options, such as bus stops or subway stations.
    private String appliances; // These are all common household appliances that are commonly found in kitchens, and are used for cooking, baking, and storing food.
}