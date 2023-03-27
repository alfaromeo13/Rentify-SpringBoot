package com.example.rentify.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

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
    private Integer userId;//we get all apartments for specific user.When user logs in he wants a list of apartments he owns
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date availableFrom, availableTo;// filters available apartments for that period
    //attributes:
    private String parking;//
    private String petsAllowed;//
    private String balcony;//
    private String pool;//
    private String airConditioning;//
    private String furnished;//
    private String wiFi;//
    private String elevator;//
    private String publicTransportDist; // Allow users to filter apartments by their proximity to public transportation options, such as bus stops or subway stations.
    private String appliances; // These are all common household appliances that are commonly found in kitchens, and are used for cooking, baking, and storing food.
}