package com.example.rentify.entity;

import javax.persistence.*;

@Entity
@Table(name = "apartments_attributes")
@IdClass(ApartmentAttributeId.class)
public class ApartmentAttribute {
    @Id
    @Column(name = "apartment_id")
    private Long apartmentId;

    @Id
    @Column(name = "attribute_name")
    private String attributeName;

    @Column(name = "attribute_value")
    private String attributeValue;

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

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }
}