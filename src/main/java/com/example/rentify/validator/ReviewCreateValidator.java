package com.example.rentify.validator;

import com.example.rentify.dto.ReviewApartmentDTO;
import com.example.rentify.dto.UserDTO;
import com.example.rentify.repository.ApartmentRepository;
import com.example.rentify.repository.ReviewRepository;
import com.example.rentify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ReviewCreateValidator implements Validator {
//validatori su read only i nikako ne smijemo da modifikujemo vr. koje smo dobili!!
    //dakle citamo i kazemo d ali je nesto validno ili ne

    //if we want to inject repository,
    // validator must be bean so we add @Component on it
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ApartmentRepository apartmentRepository;

    @Override
    public boolean supports(Class<?> clazz) { //definisemo nad kojim objektom radimo validaciju.Ako pozovemo ovaj validator
        return clazz.isAssignableFrom(ReviewApartmentDTO.class); //nad nekom drugom klasom dobicemo automatski gresku!
    }

    @Override //we write our validation logic for specific class.Ovdje vrsimo validaciju proslijedjenog objekta
    public void validate(Object target, Errors errors) {
        //target-objekat koji primamo
        // errors-interfejs u koji ubacamo ako detektujemo greske pri validaciji
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
            //error code - skraceni prevod greske(posebno korisno kod apliakcija koje podrzavaju visejezicnost
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
        if (user == null)
            errors.rejectValue("user", "user.required", "User is required!");
        else if (user.getId() == null)
            errors.rejectValue("user.id", "user.id.required", "User identifier required!");
        else if (!userRepository.existsById(user.getId()))
            errors.rejectValue("user.id", "user.id.doesn't.exist", "User with specified " +
                    "id doesn't exist");

    }
}