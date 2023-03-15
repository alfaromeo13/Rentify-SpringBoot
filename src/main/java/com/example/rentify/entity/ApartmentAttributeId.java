package com.example.rentify.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ApartmentAttributeId implements Serializable {
    private Apartment apartment;
    private Attribute attribute;
}