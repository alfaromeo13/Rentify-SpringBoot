package com.example.rentify.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserLogInDTO implements Serializable {
    private String username;
    private String password;
}