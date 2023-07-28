package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
public class RentalDTO implements Serializable {
    private Integer id;
    private Double rentalPrice;
    private StatusDTO status;
    private UserDTO user;
    private Date startDate, endDate;
}