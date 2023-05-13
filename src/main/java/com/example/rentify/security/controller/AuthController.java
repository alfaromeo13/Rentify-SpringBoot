package com.example.rentify.security.controller;

import com.example.rentify.exception.ValidationException;
import com.example.rentify.security.dto.UserCreateDTO;
import com.example.rentify.security.dto.UserLoginDTO;
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

    @PostMapping("login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginDTO userLoginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<>(jwtTokenProvider.createToken(authentication), HttpStatus.OK);
            //we send generated token back to the user
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//401!
        }
    }

    @SneakyThrows
    @PostMapping("refresh-token")
    public ResponseEntity<Map<String, String>> refresh(@RequestHeader("refresh-token") String token) {
        if (token != null && jwtTokenProvider.validateToken(token, "refresh")) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            return new ResponseEntity<>(jwtTokenProvider.createToken(authentication), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /*{
          "username" : "IVICA",
          "password" : "MARICA",
          "passwordConfirm":"MARICA",
          "firstName": "Vanja",
          "lastName":"Vukovic",
          "email":"aSDASD@ASDASDASD"
      }*/

    @SneakyThrows
    @PostMapping("register")
    public ResponseEntity<Void> register(@RequestBody UserCreateDTO userCreateDTO) {
        Errors errors = new BeanPropertyBindingResult(userCreateDTO, "userCreateDTO");
        ValidationUtils.invokeValidator(userCreateValidator, userCreateDTO, errors);
        if (errors.hasErrors()) throw new ValidationException("Register user validation failed!", errors);
        userService.register(userCreateDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}