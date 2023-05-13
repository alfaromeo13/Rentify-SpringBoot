package com.example.rentify.error;

import com.example.rentify.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

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
        errors.add(new FieldErrorDTO("images", "images.error", "Upload size exceeded!"));
        return new ResponseEntity<>(new ErrorDTO("Validation error", errors), HttpStatus.PAYLOAD_TOO_LARGE);
    }
}