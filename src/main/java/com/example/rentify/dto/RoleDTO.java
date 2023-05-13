package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;

@Data
public class RoleDTO implements Serializable {
    private Integer id;
    private String name;
}