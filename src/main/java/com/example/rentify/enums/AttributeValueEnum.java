package com.example.rentify.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AttributeValueEnum {
    No("No"),
    Yes("Yes");
    private final String value;
}