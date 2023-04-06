package com.example.rentify.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorDTO {
    private final String message;
    private final List<FieldErrorDTO> errors;
}