package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;

@Data
@EqualsAndHashCode(of = {"attribute", "attributeValue"})
public class ApartmentAttributeDTO implements Serializable {
    private Integer id;
    private AttributeDTO attribute;
    private String attributeValue;
}