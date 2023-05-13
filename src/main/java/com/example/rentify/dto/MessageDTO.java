package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;

@Data
public class MessageDTO implements Serializable {
    private Integer id;
    private String message;
    private Integer senderId;
    private Integer receiverId;
    private Integer conversationId;// important!
}