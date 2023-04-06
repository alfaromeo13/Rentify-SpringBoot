package com.example.rentify.security.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserCreateDTO implements Serializable {
    private String username;
    private String password;
    private String passwordConfirm;
    private String firstName;
    private String lastName;
    private String email;
}