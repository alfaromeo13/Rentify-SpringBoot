package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO implements Serializable {
    private Integer id;
    private String message;
    private Integer senderId;
    private Integer receiverId;
    private Integer conversationId;//important
}