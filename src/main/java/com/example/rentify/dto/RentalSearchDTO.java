package com.example.rentify.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
public class RentalSearchDTO implements Serializable {
    private Integer id;

    //ovo nam vrace u ovom formatu odgovor
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate, endDate;
}