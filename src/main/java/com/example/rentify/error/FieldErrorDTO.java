package com.example.rentify.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FieldErrorDTO {
    private final String field;//koje polje je generisalo gresku
    private final String translationKey;//error code tj kljuc za prevod
    private final String defaultMessage;
}