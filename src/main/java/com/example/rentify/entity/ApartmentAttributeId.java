package com.example.rentify.entity;

import java.io.Serializable;

public class ApartmentAttributeId implements Serializable {

    private Apartment apartment;

    private Attribute attribute;

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }
}