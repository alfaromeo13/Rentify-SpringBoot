package com.example.rentify.security.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UnauthorizedResponseDTO {
    private final String message;
    private final String exceptionMessage;
}