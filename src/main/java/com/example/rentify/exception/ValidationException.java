package com.example.rentify.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;

@Getter
@RequiredArgsConstructor
public class ValidationException extends Exception {
    private final String message; //message we send to the client
    private final Errors errors; //errors occurred in validation
}