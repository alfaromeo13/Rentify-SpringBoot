package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentAttributeDTO implements Serializable {
    private Integer id;
    private AttributeDTO attribute;
    private String attributeValue;
}