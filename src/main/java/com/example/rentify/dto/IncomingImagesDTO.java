package com.example.rentify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class IncomingImagesDTO implements Serializable {
    private Integer apartmentId;
    private MultipartFile[] images;
}