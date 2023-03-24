package com.example.rentify.mapper;

import com.example.rentify.dto.ConversationDTO;
import com.example.rentify.entity.Conversation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConversationMapper {
    List<ConversationDTO> toDTOList(List<Conversation> conversations);
}
