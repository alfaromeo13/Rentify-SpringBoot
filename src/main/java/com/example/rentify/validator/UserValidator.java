package com.example.rentify.validator;

import com.example.rentify.dto.UserDTO;
import com.example.rentify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(UserDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO user = (UserDTO) target;
        validateId(user.getId(), errors);
        validateEmail(user.getEmail(), errors);
        validateUsername(user.getUsername(), errors);
        validateFirstName(user.getFirstName(), errors);
        validateLastName(user.getLastName(), errors);
    }

    private void validateUsername(String username, Errors errors) {
        if (username == null)
            errors.rejectValue("username", "username.required", "username is required!");
        else if (username.trim().equals("")) //if username is empty
            errors.rejectValue("username", "username.empty", "username is empty!");
        else if (userRepository.existsByUsername(username))
            errors.rejectValue("username", "username.exists", "username already exists!");
    }

    private void validateEmail(String email, Errors errors) {
        if (email == null)
            errors.rejectValue("email", "email.required", "Email is required!");
        else if (email.trim().equals("")) //if email is empty
            errors.rejectValue("email", "email.empty", "Email is empty!");
    }

    private void validateLastName(String lastName, Errors errors) {
        if (lastName == null)
            errors.rejectValue("lastName", "lastName.required", "last name is required!");
        else if (lastName.trim().equals("")) //if lastName is empty
            errors.rejectValue("lastName", "lastName.empty", "last name is empty!");
    }

    private void validateFirstName(String firstName, Errors errors) {
        if (firstName == null)
            errors.rejectValue("firstName", "firstName.required", "first name is required!");
        else if (firstName.trim().equals("")) //if firstName is empty
            errors.rejectValue("firstName", "firstName.empty", "first name is empty!");
    }

    private void validateId(Integer id, Errors errors) {
        if (id != null) errors.rejectValue("id", "id.error", "Id should't be sent!");
    }
}