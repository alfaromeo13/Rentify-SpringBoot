package com.example.rentify.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RentalApartmentDTO extends RentalDTO {
    private Integer apartmentId;
}