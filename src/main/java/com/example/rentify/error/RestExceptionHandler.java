package com.example.rentify.error;

import com.example.rentify.exception.ValidationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)//exception class
    public ResponseEntity<ErrorDTO> handleValidationException(ValidationException e) {
        //this method is called when any controller throws exception of type ValidationException
        Errors errors = e.getErrors(); //validation errors
        List<FieldError> fieldErrors = errors.getFieldErrors();
        List<FieldErrorDTO> customFieldErrors = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            FieldErrorDTO fieldErrorDTO = new FieldErrorDTO(
                    fieldError.getField(),
                    fieldError.getCode(),
                    fieldError.getDefaultMessage()
            );
            customFieldErrors.add(fieldErrorDTO);
        } //return type is ResponseEntity
        ErrorDTO errorDTO = new ErrorDTO(e.getMessage(), customFieldErrors);
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorDTO> handleUploadSizeException() {
        List<FieldErrorDTO> errors = new ArrayList<>();
        errors.add(new FieldErrorDTO("images", "images.error",
                "Maximum upload size exceeded. Maximum is 100MB"));
        return new ResponseEntity<>(new ErrorDTO("Validation error", errors), HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler({JsonMappingException.class, InvalidFormatException.class, JsonParseException.class})
    public ResponseEntity<ErrorDTO> handleInvalidFormatException() {
        List<FieldErrorDTO> customFieldErrors = new ArrayList<>();
        customFieldErrors.add(new FieldErrorDTO("apartments", "apartment.error", "Invalid input format"));
        ErrorDTO errorDTO = new ErrorDTO("Validation error", customFieldErrors);
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
}