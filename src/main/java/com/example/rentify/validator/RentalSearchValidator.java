package com.example.rentify.validator;

import com.example.rentify.dto.RentalSearchDTO;
import com.example.rentify.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class RentalSearchValidator implements Validator {

    private final ApartmentService apartmentService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RentalSearchDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RentalSearchDTO rental = (RentalSearchDTO) target;
        validateApartment(rental.getId(), errors);
        validateDate(rental.getStartDate(), rental.getEndDate(), errors);
    }

    private void validateDate(Date startDate, Date endDate, Errors errors) {
        if (startDate == null)
            errors.rejectValue("startDate", "startDate.required", "Missing starting date!");
        else if (endDate == null)
            errors.rejectValue("endDate", "endDate.required", "Missing ending date!");
        else if (startDate.after(endDate))
            errors.rejectValue("startDate", "startDate.error", "Wrong input format!");
        else if (endDate.before(startDate))
            errors.rejectValue("endDate", "endDate.error", "Wrong input format!");
    }

    private void validateApartment(Integer apartmentId, Errors errors) {
        if (apartmentId == null)
            errors.rejectValue("apartmentId", "apartment.id.required", "Apartment id is required!");
        else if (apartmentService.existsById(apartmentId))
            errors.rejectValue("apartmentId", "invalid.id", "Invalid apartment id!");
    }
}