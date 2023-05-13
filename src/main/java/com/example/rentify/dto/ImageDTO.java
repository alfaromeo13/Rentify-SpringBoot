package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;

@Data
@EqualsAndHashCode(of = {"id", "path"})
public class ImageDTO implements Serializable {
    private Integer id;
    private String path;
    private Double size;
}