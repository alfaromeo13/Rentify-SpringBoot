package com.example.rentify.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Double rentalPrice;
    private StatusDTO status;
    private UserDTO user;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startDate, endDate;
}