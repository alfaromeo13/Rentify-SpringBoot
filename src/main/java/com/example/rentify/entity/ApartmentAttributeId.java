package com.example.rentify.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentAttributeId implements Serializable {
    private Apartment apartment;
    private Attribute attribute;
}