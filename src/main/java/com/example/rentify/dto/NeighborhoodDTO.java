package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NeighborhoodDTO implements Serializable {
    private Integer id;
    private String name;
}
