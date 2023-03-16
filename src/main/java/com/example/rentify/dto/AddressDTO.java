package com.example.rentify.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private Integer id;
    private String street;
    private String number;
    private String code;
}