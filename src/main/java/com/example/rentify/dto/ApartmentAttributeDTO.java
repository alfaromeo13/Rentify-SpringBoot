package com.example.rentify.dto;

import com.example.rentify.enums.AttributeValueEnum;
import lombok.*;

import java.io.Serializable;

@Data
@EqualsAndHashCode(of = {"attribute", "attributeValue"})
public class ApartmentAttributeDTO implements Serializable {
    private Integer id;
    private AttributeDTO attribute;
    private AttributeValueEnum attributeValue; //values can be only defines ones
}