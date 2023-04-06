package com.example.rentify.security.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class UnauthorizedResponseDTO implements Serializable {
    private final String message;
    private final String exceptionMessage;
}