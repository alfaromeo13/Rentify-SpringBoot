package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO implements Serializable {
    private Integer id;
    private String street;
    private String number;
    private String code;
}