package com.example.rentify.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Integer id;
    private Integer grade;
    private String comment;
    private Date createdAt;
    private Boolean isActive;

    private UserDTO user;
}
