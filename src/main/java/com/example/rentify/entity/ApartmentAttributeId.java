package com.example.rentify.entity;

import java.io.Serializable;

public class ApartmentAttributeId implements Serializable {

    private Integer apartmentId;

    private String attributeName;

    public Integer getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Integer apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
}