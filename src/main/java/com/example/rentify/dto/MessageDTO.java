package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
public class MessageDTO implements Serializable {
    private Date timestamp;
    private String message;
    private String sender;
}