package com.example.rentify.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateConversationDTO implements Serializable {
    String usernameFrom;
    String usernameTo;
}