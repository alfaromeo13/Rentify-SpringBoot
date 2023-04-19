package com.example.rentify.validator;

import com.example.rentify.dto.ApartmentDTO;
import com.example.rentify.dto.RentalDTO;
import com.example.rentify.dto.UserDTO;
import com.example.rentify.repository.ApartmentRepository;
import com.example.rentify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class RentalValidator implements Validator {
    private final UserRepository userRepository;
    private final ApartmentRepository apartmentRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RentalDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RentalDTO rental = (RentalDTO) target;
        validateId(rental.getId(), errors);
        validatePrice(rental.getRentalPrice(), errors);
        validateUser(rental.getUser(), errors);
        validateApartment(rental.getApartment(), errors);
        validateDate(rental.getStartDate(), rental.getEndDate(), errors);
    }

    private void validatePrice(Double rentalPrice, Errors errors) {
        if (rentalPrice == null)
            errors.rejectValue("rentalPrice", "rentalPrice.missing", "Missing rental price!");
        else if (rentalPrice < 0)
            errors.rejectValue("rentalPrice", "rentalPrice.error", "Negative rental price!");
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

    private void validateId(Integer id, Errors errors) {
        if (id != null) errors.rejectValue("id", "id.error", "Id should't be sent!");
    }

    private void validateApartment(ApartmentDTO apartment, Errors errors) {
        if (apartment == null)
            errors.rejectValue("apartment", "apartment.required", "Apartment is required!");
        else if (apartment.getId() == null)
            errors.rejectValue("apartmentId", "apartment.id.required", "Apartment id is required!");
        else if (!apartmentRepository.existsById(apartment.getId()))
            errors.rejectValue("apartmentId", "invalid.id", "Invalid apartment id!");
    }

    private void validateUser(UserDTO user, Errors errors) {
        if (user == null)
            errors.rejectValue("user", "user.required", "User is required!");
        else if (user.getId() == null)
            errors.rejectValue("user.id", "user.id.required", "User identifier required!");
        else if (!userRepository.existsById(user.getId()))
            errors.rejectValue("user.id", "user.id.doesn't.exist", "User with specified " +
                    "id doesn't exist");
    }
}
