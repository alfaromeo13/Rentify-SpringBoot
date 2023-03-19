package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO implements Serializable {
    private Integer id;
    private Integer grade;
    private String comment;
    private Date createdAt;
    private Boolean isActive;

    private UserDTO user;//user who made review
}
