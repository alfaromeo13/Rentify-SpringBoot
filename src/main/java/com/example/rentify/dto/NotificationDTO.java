package com.example.rentify.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class NotificationDTO implements Serializable {
    private Integer id;
    private String message;
    private Date createdAt;
    private String senderUsername;
    private String receiverUsername;
}