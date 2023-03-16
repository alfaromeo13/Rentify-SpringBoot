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
    private Integer id;
    private String title;
    private String description;
    private Integer sqMeters;
    private Double price;
    private Integer numOfBedrooms;
    private Integer numOfBathrooms;
    private String number;
    /*
        private String city;
    private Integer minNumOfBedrooms;
    private Integer maxNumOfBedrooms;
    private Integer minNumOfBathrooms;
    private Integer maxNumOfBathrooms;
    private Double minPrice;
    private Double maxPrice;
    private Integer minSquareMeters;
    private Integer maxSquareMeters;
     */


    private String addressStreet;//we need join for this
    private String cityName;//we need join
}