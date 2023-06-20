package com.example.rentify.security.validator;

import com.example.rentify.repository.UserRepository;
import com.example.rentify.security.dto.UserCreateDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserCreateValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(UserCreateDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreateDTO userCreateDTO = (UserCreateDTO) target;
        validateUsername(userCreateDTO.getUsername(), errors);
        validatePassword(userCreateDTO.getPassword(), userCreateDTO.getPasswordConfirm(), errors);
        validateFirstName(userCreateDTO.getFirstName(), errors);
        validateLastName(userCreateDTO.getLastName(), errors);
        validateEmail(userCreateDTO.getEmail(), errors);
    }

    /**
     * Validate username
     *
     * @param username given username
     * @param errors   reference to Errors
     */
    private void validateUsername(String username, Errors errors) {
        if (StringUtils.isBlank(username))
            errors.rejectValue("username", "username.invalid", "Username is not valid!");
        else if (StringUtils.isNotBlank(username) && userRepository.existsByUsername(username))
            errors.rejectValue("username", "username.invalid", "Username already taken!");
    }

    /**
     * Validate password
     *
     * @param password        given password
     * @param passwordConfirm given password confirmation
     * @param errors          reference to Errors
     */
    private void validatePassword(String password, String passwordConfirm, Errors errors) {
        if (StringUtils.isBlank(password))
            errors.rejectValue("password", "password.invalid", "Password is not valid!");

        if (StringUtils.isBlank(passwordConfirm)) {
            errors.rejectValue(
                    "passwordConfirm",
                    "passwordConfirm.invalid",
                    "Password confirm is not valid!"
            );
        }

        if (StringUtils.isNotBlank(password) && !password.equals(passwordConfirm)) {
            errors.rejectValue(
                    "password",
                    "password.no-matched-with-confirmed-password",
                    "Password is not same as confirmed password"
            );
        }

        if(StringUtils.isNotBlank(password) && password.length() < 8){
            errors.rejectValue(
                    "password",
                    "password.error",
                    "Password must be at least 8 characters long");
        }
    }

    /**
     * Validate first name
     *
     * @param firstName given first name
     * @param errors    reference to Errors
     */
    private void validateFirstName(String firstName, Errors errors) {
        if (StringUtils.isBlank(firstName))
            errors.rejectValue("firstName", "firstName.required", "First name is required!");
    }

    /**
     * Validate last name
     *
     * @param lastName given last name
     * @param errors   reference to Errors
     */
    private void validateLastName(String lastName, Errors errors) {
        if (StringUtils.isBlank(lastName))
            errors.rejectValue("lastName", "lastName.required", "Last name is required!");
    }

    /**
     * Validate email
     *
     * @param email  given email
     * @param errors reference to Errors
     */
    private void validateEmail(String email, Errors errors) {
        if (StringUtils.isBlank(email))
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
}