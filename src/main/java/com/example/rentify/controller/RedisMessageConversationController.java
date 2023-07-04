package com.example.rentify.controller;

import com.example.rentify.dto.MessageDTO;
import com.example.rentify.entity.RedisConversation;
import com.example.rentify.repository.RedisConversationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class RedisMessageConversationController {
    //za slanje poruka i upis u redis

    private final SimpMessagingTemplate template;
    private final RedisConversationRepository redisConversationRepository;

    @SneakyThrows
    @MessageMapping("/receive/{from-username}/{to-username}")
    public void receiveMessage(@DestinationVariable("from-user") String user1,
                               @DestinationVariable("from-user") String user2,
                               @Payload String payload) {
        RedisConversation redisConversation = new RedisConversation();
        redisConversation.setUsername1(user1);
        redisConversation.setUsername2(user2);
        ObjectMapper objectMapper = new ObjectMapper();

        MessageDTO message = objectMapper.readValue(payload, MessageDTO.class);
        redisConversation.setMessage(message);
        redisConversationRepository.save(redisConversation);
        template.convertAndSend("",message);
    }
}
