package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RentalDTO implements Serializable {
    private Integer id;
    private Date startDate;
    private Date endDate;
    private Double rentalPrice;
    private Boolean available;
    private UserDTO user;
    private ApartmentDTO apartment;
}