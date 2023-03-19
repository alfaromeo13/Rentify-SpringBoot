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
    private String propertyType;//Apartment/condo/house/townhouse  <- ?
    private String number;//phone number of owner  //
    private String addressStreet;//we need join //
    private String neighborhoodName;//Allow users to search for apartments by specific neighborhoods //
    private String cityName;//we need join //
    private String countryName; //
    private String availabilityDate;//<-
    private Integer userId;
    //attributes:
    private Boolean parkingType;//garage or street parking.
    private Boolean pool;
    private Boolean fitnessCenter;
    private Boolean petsAllowed;
    //
    private Boolean balcony;
    private Boolean deck;
    private Boolean patio;
    //
    private Boolean airConditioning;
    private Boolean washerUnits;
    private Boolean dryerUnits;
    private Boolean furnished;
    private Boolean heatIncluded;
    private Boolean apartment;
    private Boolean house;
    private Boolean townhouse;
    private Boolean corporate;
    private Boolean furnishedAvailable;
    private Boolean assistedLiving;
    private Boolean cableReady;
    private Boolean wiFi;
    private Boolean controlledAccess;
    private Boolean elevator;
    private Boolean extraStorage;
    private Boolean subletsAllowed;
    private Integer distanceToPublicTransportation;//Allow users to filter apartments by their proximity to public transportation options, such as bus stops or subway stations.
    private Boolean utilities;
    private Boolean smokingAllowed;
    private Boolean storageUnit;
    private Boolean refrigerator;
    private Boolean microwave;
    private Boolean range;
    private Boolean oven;
    private Boolean freezer;
}