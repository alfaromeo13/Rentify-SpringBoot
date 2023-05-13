package com.example.rentify.validator;

import com.example.rentify.dto.UserDTO;
import com.example.rentify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserUpdateValidator implements Validator {

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
        if (username != null)
            errors.rejectValue("username", "username.error", "Username should't be sent!");
    }

    private void validateEmail(String email, Errors errors) {
        if (StringUtils.isBlank(email)) //if email is empty
            errors.rejectValue("email", "email.required", "Email is required!");
        else if (!patternMatches(email))
            errors.rejectValue("email", "email.error", "Email is invalid!");
        else if (userRepository.existsByEmail(email.toLowerCase()))
            errors.rejectValue("email", "email.invalid", "Email already taken!");
    }

    private boolean patternMatches(String email) {
        return Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$").matcher(email).matches();
    }

    private void validateLastName(String lastName, Errors errors) {
        if (lastName == null)
            errors.rejectValue("lastName", "lastName.required", "Last name is required!");
        else if (lastName.trim().equals("")) //if lastName is empty
            errors.rejectValue("lastName", "lastName.empty", "Last name is empty!");
    }

    private void validateFirstName(String firstName, Errors errors) {
        if (firstName == null)
            errors.rejectValue("firstName", "firstName.required", "First name is required!");
        else if (firstName.trim().equals("")) //if firstName is empty
            errors.rejectValue("firstName", "firstName.empty", "First name is empty!");
    }

    private void validateId(Integer id, Errors errors) {
        if (id != null) errors.rejectValue("id", "id.error", "Id should't be sent!");
    }
}