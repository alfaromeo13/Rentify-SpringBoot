package com.example.rentify.entity;

import java.io.Serializable;

public class ApartmentAttributeId implements Serializable {

    private Long apartmentId;

    private String attributeName;

    public Long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
}