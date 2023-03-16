package com.example.rentify.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentAttributeDTO {
    private AttributeDTO attribute;
    private String attributeValue;
}