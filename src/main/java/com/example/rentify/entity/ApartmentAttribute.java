package com.example.rentify.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "apartments_attributes")
@IdClass(ApartmentAttributeId.class)
//defined composite key in ApartmentAttributeId class
public class ApartmentAttribute {
    @Id
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    @Id
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_name")
    private Attribute attribute;

    @Column(name = "attribute_value")
    private String attributeValue;
}