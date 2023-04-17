package com.example.rentify.error;

import com.example.rentify.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ValidationException.class)//klasa koja predstavlja izuzetak
    public ResponseEntity<ErrorDTO> handleValidationException(ValidationException e) {
        //vidimo da primamo objekat tog izuzetka kao argument
        //dakle kada se u nekom kontoleru dogodi erxception tipa ValidationException klase ova metoda ce se pozvati
        //povratni tip ove metode logicno treba da bude ResponseEntity :D
        Errors errors = e.getErrors();
        List<FieldError> fieldErrors = errors.getFieldErrors();//lista validacionih gresaka u procesu validacije
        List<FieldErrorDTO> customFieldErrors = new ArrayList<>();
        //sve ove greske trebamo vratiti nazad klijentu
        for (FieldError fieldError : fieldErrors) {
            //za svaki field error kreiramo nas custom field error dto
            FieldErrorDTO fieldErrorDTO = new FieldErrorDTO(
                    fieldError.getField(),
                    fieldError.getCode(),
                    fieldError.getDefaultMessage()
            );
            customFieldErrors.add(fieldErrorDTO);
        }
        ErrorDTO errorDTO = new ErrorDTO(e.getMessage(), customFieldErrors);
        //sada ce nas errorDTO biti popunjem porukom i greskama koje su se desile u samom validatoru
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
    //ako zelimo da hendlujemo neki drugi excpetion koji se desava na niuvou neke druge metode u kontroleru
    //ovdje pravimo novu metodu i vidimo sta zelimo da vratimo
}