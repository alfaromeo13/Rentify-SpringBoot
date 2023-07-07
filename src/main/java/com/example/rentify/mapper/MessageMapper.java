package com.example.rentify.mapper;

import com.example.rentify.dto.MessageDTO;
import com.example.rentify.entity.Message;
import com.example.rentify.entity.RedisConversation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    List<MessageDTO> toDTOList(List<Message> messages);
    Message toEntity(MessageDTO messageDTO);
}
