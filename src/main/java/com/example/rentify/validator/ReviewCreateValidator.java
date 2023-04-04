package com.example.rentify.validator;

import com.example.rentify.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ReviewCreateValidator implements Validator {
    //if we want to inject repository,
    // validator must be bean so we add @Component on it
    private final ReviewRepository reviewRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override //we write our validation logic for specific class
    public void validate(Object target, Errors errors) {

    }
}