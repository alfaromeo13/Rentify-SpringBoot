package com.example.rentify.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
public class RentalDTO implements Serializable {
    private Integer id;
    private Double rentalPrice;
    private StatusDTO status;
    private UserDTO user;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate, endDate;
}