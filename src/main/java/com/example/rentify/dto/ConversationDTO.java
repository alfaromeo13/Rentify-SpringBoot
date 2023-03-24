package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDTO implements Serializable {
    private Integer id;
    private UserDTO user1;
    private UserDTO user2;
    private Date timestamp;
    private Boolean isActive;
}