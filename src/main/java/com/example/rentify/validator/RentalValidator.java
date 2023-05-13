package com.example.rentify.validator;

import com.example.rentify.dto.RentalApartmentDTO;
import com.example.rentify.dto.StatusDTO;
import com.example.rentify.dto.UserDTO;
import com.example.rentify.repository.ApartmentRepository;
import com.example.rentify.repository.RentalRepository;
import com.example.rentify.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class RentalValidator implements Validator {

    private final RentalService rentalService;
    private final RentalRepository rentalRepository;
    private final ApartmentRepository apartmentRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RentalApartmentDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RentalApartmentDTO rental = (RentalApartmentDTO) target;
        validateId(rental.getId(), errors);
        validatePrice(rental.getRentalPrice(), errors);
        validateUser(rental.getUser(), errors);
        validateApartment(rental.getApartmentId(), errors);
        validateStatus(rental.getStatus(), errors);
        validateDate(rental, errors);
    }

    private void validateStatus(StatusDTO status, Errors errors) {
        if (status != null)
            errors.rejectValue("status", "status.error", "Status should't be sent!");
    }

    private void validatePrice(Double rentalPrice, Errors errors) {
        if (rentalPrice != null)
            errors.rejectValue("rentalPrice", "rentalPrice.error", "Renting price should't be sent!");
    }

    private void validateDate(RentalApartmentDTO rental, Errors errors) {
        Integer id = rental.getApartmentId();
        Date startDate = rental.getStartDate();
        Date endDate = rental.getEndDate();
        if (startDate == null)
            errors.rejectValue("startDate", "startDate.required", "Missing starting date!");
        else if (endDate == null)
            errors.rejectValue("endDate", "endDate.required", "Missing ending date!");
        else if (startDate.before(new Date()))
            errors.rejectValue("startDate", "startDate.error", "Start date can't be in past!");
        else if (endDate.before(new Date()))
            errors.rejectValue("endDate", "endDate.error", "End date can't be in past!");
        else if (startDate.after(endDate))
            errors.rejectValue("startDate", "startDate.error", "Start after end!");
        else if (startDate.equals(endDate))
            errors.rejectValue("startDate", "startDate.error", "Start date same as end date!");
        else if (notValid(startDate.toString()))
            errors.rejectValue("startDate", "startDate.error", "Start date not valid!");
        else if (notValid(endDate.toString()))
            errors.rejectValue("endDate", "endDate.error", "End date not valid!");
        else if (!rentalRepository.findForPeriod(id, startDate, endDate).isEmpty())
            errors.rejectValue("startDate",
                    "interval.error", "Apartment is already rented for that period!");
        else if (rentalService.calculatePrice(rental) == 0)
            errors.rejectValue("endDate", "endDate.error",
                    "Renting period can't be shorter than defined period");
    }

    private boolean notValid(String inputDateString) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inputDateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void validateId(Integer id, Errors errors) {
        if (id != null) errors.rejectValue("id", "id.error", "Id should't be sent!");
    }

    private void validateApartment(Integer apartmentId, Errors errors) {
        if (apartmentId == null)
            errors.rejectValue(
                    "apartmentId", "apartment.id.required", "Apartment id is required!");
        else if (!apartmentRepository.existsById(apartmentId))
            errors.rejectValue("apartmentId", "invalid.id", "Invalid apartment id!");
    }

    private void validateUser(UserDTO user, Errors errors) {
        if (user != null)
            errors.rejectValue("user", "user.error", "User should't be sent!");
    }
}