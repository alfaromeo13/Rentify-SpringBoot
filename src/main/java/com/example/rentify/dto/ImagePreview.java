package com.example.rentify.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ImagePreview {
    private final String value; //base64 image string
    private final String type;
}