package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentAttributeDTO implements Serializable {
    private AttributeDTO attribute;
    private String attributeValue;
}