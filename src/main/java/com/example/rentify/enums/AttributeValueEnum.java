package com.example.rentify.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AttributeValueEnum {
    //Pets Allowed, Balcony, Air Conditioning, Elevator
    NO("No"),
    YES("Yes"),
    //Appliances, Wifi
    INCLUDED("Included"),
    NOT_INCLUDED("Not included"),
    //Parking
    UNDERGROUND("Underground"),
    STREET_PARKING("Street parking"),
    //Furnished
    UNFURNISHED("Unfurnished"),
    FULLY_FURNISHED("Fully furnished"),
    PARTIALLY_FURNISHED("Partially furnished");

    private final String value;
}