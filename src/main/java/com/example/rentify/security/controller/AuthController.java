package com.example.rentify.security.controller;

import com.example.rentify.exception.ValidationException;
import com.example.rentify.security.dto.UserCreateDTO;
import com.example.rentify.security.dto.UserLoginDTO;
import com.example.rentify.security.jwt.JwtTokenProvider;
import com.example.rentify.security.validator.UserCreateValidator;
import com.example.rentify.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authenticate")
public class AuthController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserCreateValidator userCreateValidator;
    private final AuthenticationManager authenticationManager;//security configuration bean

    @PostMapping("login") // POST http://localhost:8080/api/authenticate/login
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                    (userLoginDTO.getUsername(), userLoginDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            if (!userService.isActive(authentication.getName())) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<>(jwtTokenProvider.createToken(authentication), HttpStatus.OK);
            //we send generated token back to user
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//401!
        }
    }

    @SneakyThrows
    @PostMapping("refresh-token")
    public ResponseEntity<Map<String, String>> refresh(@RequestHeader("refresh-token") String token) {
        if (!token.isEmpty() && jwtTokenProvider.validateToken(token, "refresh")) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            if (!userService.isActive(authentication.getName())) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(jwtTokenProvider.createToken(authentication), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @SneakyThrows
    @PostMapping("register")
    public ResponseEntity<Void> register(@RequestBody UserCreateDTO userCreateDTO) {
        Errors errors = new BeanPropertyBindingResult(userCreateDTO, "userCreateDTO");
        ValidationUtils.invokeValidator(userCreateValidator, userCreateDTO, errors);
        if (errors.hasErrors()) throw new ValidationException("Register user validation failed!", errors);
        userService.register(userCreateDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/verify") //http://localhost:8080/api/authenticate/verify?mail=...&code=...
    public ResponseEntity<Void> verifyAccount(@RequestParam String mail,@RequestParam String code) {
            return new ResponseEntity<>(userService.activateAccount(mail,code) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/request-reset-password") //http://localhost:8080/api/authenticate/request-reset-password?mail=...
    public ResponseEntity<String> requestResetPassword(@RequestParam String mail) {
        if (userService.sendResetMail(mail)) return ResponseEntity.ok("Reset mail sent");
        else return new ResponseEntity<>("Account not found..", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> changePassword(@RequestParam String mail, @RequestParam("inputField") String input) {
        if (userService.changePassword(mail, input)) return ResponseEntity.ok("Password has been changed!");
        else return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
    }
}