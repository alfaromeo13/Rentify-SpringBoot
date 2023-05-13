package com.example.rentify.dto;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class RentalApartmentDTO extends RentalDTO {
    private Integer apartmentId;
}