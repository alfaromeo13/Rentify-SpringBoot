package com.example.rentify.validator;

import com.example.rentify.dto.ReviewApartmentDTO;
import com.example.rentify.dto.UserDTO;
import com.example.rentify.repository.ApartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ReviewValidator implements Validator {

    private final ApartmentRepository apartmentRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        //definisemo nad kojim objektom radimo validaciju.Ako pozovemo ovaj validator
        return clazz.isAssignableFrom(ReviewApartmentDTO.class); //nad nekom drugom klasom dobicemo automatski gresku!
    }

    @Override //Ovdje vrsimo validaciju proslijedjenog objekta
    public void validate(Object target, Errors errors) {
        //target-objekat koji primamo
        //ako je errors prazan znamo da je validacija prosla uspjesno i po tome gledamo!!
        ReviewApartmentDTO review = (ReviewApartmentDTO) target;
        validateApartmentId(review.getApartmentId(), errors);
        validateComment(review.getComment(), errors);
        validateGrade(review.getGrade(), errors);
        validateUser(review.getUser(), errors);
        validateId(review.getId(), errors);
    }

    private void validateId(Integer id, Errors errors) {
        if (id != null)
            errors.rejectValue("id", "id.error", "Id should't be sent!");
    }

    private void validateComment(String comment, Errors errors) {
        if (comment == null) {
            //field - naziv polja iz klase koji nije validan tj nad kojim se desila greska
            //error code - skraceni prevod greske(posebno korisno kod apliakcija koje podrzavaju visejezicnost)
            //prevodom se ne bavi bekend vec frontend klijentska aplikacija
            errors.rejectValue("comment", "comment.required", "Comment is required!");
        } else if (comment.trim().equals("")) { //if comment is empty
            errors.rejectValue("comment", "comment.empty", "Comment is empty!");
        }
//        if(comment.length()>128)
//            errors.rejectValue("name","name.length-exceeded","name length is not valid!");
    }

    private void validateGrade(Integer grade, Errors errors) {
        if (grade == null)
            errors.rejectValue("grade", "grade.required", "Grade is required!");
        else if (grade < 1 || grade > 5)
            errors.rejectValue("grade", "bad.grade", "Bad grade entered!");
    }

    private void validateApartmentId(Integer apartmentId, Errors errors) {
        if (apartmentId == null)
            errors.rejectValue("apartmentId", "apartment.id.required", "Apartment id is required!");
        else if (!apartmentRepository.existsById(apartmentId))
            errors.rejectValue("apartmentId", "invalid.id", "Invalid apartment id!");
    }

    private void validateUser(UserDTO user, Errors errors) {
        if (user != null)
            errors.rejectValue("user", "user.error", "User should't be sent!");
    }
}