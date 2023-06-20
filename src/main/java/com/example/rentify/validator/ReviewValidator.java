package com.example.rentify.validator;

import com.example.rentify.dto.ReviewApartmentDTO;
import com.example.rentify.dto.UserDTO;
import com.example.rentify.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ReviewValidator implements Validator {

    private final ApartmentService apartmentService;

    @Override
    public boolean supports(Class<?> clazz) {
        //we define on which object we do validation(if we call on other object there will be exception)
        return clazz.isAssignableFrom(ReviewApartmentDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        //target - object which we validate
        //if 'errors' is empty validation was successful
        ReviewApartmentDTO review = (ReviewApartmentDTO) target;
        validateApartmentId(review.getApartmentId(), errors);
        validateComment(review.getComment(), errors);
        validateGrade(review.getGrade(), errors);
        validateUser(review.getUser(), errors);
        validateId(review.getId(), errors);
    }

    private void validateId(Integer id, Errors errors) {
        if (id != null) errors.rejectValue("id", "id.error", "Id should't be sent!");
    }

    private void validateComment(String comment, Errors errors) {
        if (comment == null) {
            //field - name of field from class which is not valid
            //error code - short translation of an error(we will translate later on frontend)
            errors.rejectValue("comment", "comment.required", "Comment is required!");
        } else if (comment.trim().equals("")) { //if comment is empty
            errors.rejectValue("comment", "comment.empty", "Comment is empty!");
        }
    }

    private void validateGrade(Integer grade, Errors errors) {
        if (grade == null)
            errors.rejectValue("grade", "grade.required", "Grade is required!");
        else if (grade < 1 || grade > 5)
            errors.rejectValue("grade", "bad.grade", "Bad grade entered!");
    }

    private void validateApartmentId(Integer apartmentId, Errors errors) {
        if (apartmentId == null)
            errors.rejectValue("apartmentId",
                    "apartment.id.required",
                    "Apartment id is required!");
        else if (!apartmentService.existsById(apartmentId))
            errors.rejectValue("apartmentId", "invalid.id", "Invalid apartment id!");
    }

    private void validateUser(UserDTO user, Errors errors) {
        if (user != null) errors.rejectValue("user", "user.error", "User should't be sent!");
    }
}