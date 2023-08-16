package com.example.rentify.security.validator;

import com.example.rentify.repository.UserRepository;
import com.example.rentify.security.dto.UserCreateDTO;
import com.example.rentify.security.dto.UserLoginDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class LoginValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(UserLoginDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserLoginDTO userLoginDTO = (UserLoginDTO) target;
        validateUsername(userLoginDTO.getUsername(), errors);
        validatePassword(userLoginDTO.getPassword(), errors);
    }

    private void validateUsername(String username, Errors errors) {
        if (StringUtils.isBlank(username))
            errors.rejectValue("username", "username.invalid", "Username is missing");
        else if(!userRepository.existsByUsername(username))
            errors.rejectValue("username", "username.invalid", "Username not found");
    }

    private void validatePassword(String password, Errors errors) {
        if (StringUtils.isBlank(password))
            errors.rejectValue("password", "password.invalid", "Password is empty");
    }
}