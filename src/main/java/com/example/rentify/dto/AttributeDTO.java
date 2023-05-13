package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;


@Data
@EqualsAndHashCode
public class AttributeDTO implements Serializable {
    private String name;
}