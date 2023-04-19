package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StatusDTO implements Serializable {
    private String name;
}
