package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FullUserDTO extends UserDTO implements Serializable {
    private List<ApartmentDTO> favoriteApartments;
    //private List<ConversationDTO> sender = new ArrayList<>();
    // ako ovo iznad treba ne treba messages lista i makni one relacije iz User entiteta
    private List<ApartmentDTO> apartments;
    private List<MessageDTO> messagesFromSender;
}