package com.example.rentify.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FieldErrorDTO {
    private final String field;//field which triggered exception
    private final String translationKey;//error code
    private final String defaultMessage;
}