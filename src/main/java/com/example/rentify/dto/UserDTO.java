package com.example.rentify.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserDTO implements Serializable {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;
    private Boolean isActive;
    //no password!!!
}