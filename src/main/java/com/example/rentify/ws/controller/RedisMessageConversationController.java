package com.example.rentify.ws.controller;

import com.example.rentify.dto.MessageDTO;
import com.example.rentify.entity.RedisConversation;
import com.example.rentify.ws.TopicConstants;
import com.example.rentify.ws.repository.RedisConversationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Controller
@RequiredArgsConstructor //this isn't rest controller
public class RedisMessageConversationController {

    private final SimpMessagingTemplate messagingTemplate;
    private final RedisConversationRepository redisConversationRepository;

    @SneakyThrows
    @MessageMapping("/receive/{conversationId}")
    public void receiveMessage(@DestinationVariable("conversationId") String conversationId,
                               @Payload String payload) {
        RedisConversation conversation = redisConversationRepository
                .findById(conversationId)
                .orElseThrow(() -> new EntityNotFoundException("Conversation not exists. Id: " + conversationId));

        MessageDTO messageDTO = new ObjectMapper().readValue(payload, MessageDTO.class);
        conversation.appendMessage(messageDTO);
        conversation.setIsOpened(false);
        redisConversationRepository.save(conversation);
        messagingTemplate.convertAndSend(TopicConstants.CONVERSATION_TOPIC + conversationId, messageDTO);
    }
}
