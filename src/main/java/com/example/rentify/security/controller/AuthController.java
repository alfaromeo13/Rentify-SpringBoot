package com.example.rentify.security.controller;

import com.example.rentify.exception.ValidationException;
import com.example.rentify.security.dto.UserCreateDTO;
import com.example.rentify.security.dto.UserLogInDTO;
import com.example.rentify.security.jwt.JwtTokenProvider;
import com.example.rentify.security.validator.UserCreateValidator;
import com.example.rentify.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authenticate")
public class AuthController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserCreateValidator userCreateValidator;
    private final AuthenticationManager authenticationManager;//security configuration bean

    /* POST http://localhost:8080/api/authenticate/login
        {
            "username" : "johndoe",
            "password" : "hiperbola12345!"
        }
     */

    @PostMapping(value = "login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLogInDTO userLoginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.createToken(authentication);//token is generated and we send it back to user
            return new ResponseEntity<>(Collections.singletonMap("token", token), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//401!
        }
    }

    /*
      {
          "username" : "IVICA",
          "password" : "MARICA",
          "passwordConfirm":"MARICA",
          "firstName": "Vanja",
          "lastName":"Vukovic"
          "email":"aSDASD@ASDASDASD"
      }
   */

    @SneakyThrows
    @PostMapping(value = "register")
    public ResponseEntity<Void> register(@RequestBody UserCreateDTO userCreateDTO) {
        Errors errors = new BeanPropertyBindingResult(userCreateDTO, "userCreateDTO");
        ValidationUtils.invokeValidator(userCreateValidator, userCreateDTO, errors);
        if (errors.hasErrors()) throw new ValidationException("Register user validation failed!", errors);
        userService.register(userCreateDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @SneakyThrows
    @PostMapping("refresh-token")//???
    public ResponseEntity<Map<String, String>> refresh(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            boolean isValid = jwtTokenProvider.validateToken(token);
            if (isValid) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                String newToken = jwtTokenProvider.createToken(authentication);
                return new ResponseEntity<>(Collections.singletonMap("token", newToken), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}