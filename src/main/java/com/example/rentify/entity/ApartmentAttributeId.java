package com.example.rentify.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentAttributeId implements Serializable {
    private Apartment apartment;
    private Attribute attribute;
}