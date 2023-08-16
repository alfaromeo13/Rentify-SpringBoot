package com.example.rentify.service;

import com.example.rentify.dto.MessageDTO;
import com.example.rentify.entity.RedisConversation;
import com.example.rentify.ws.TopicConstants;
import com.example.rentify.ws.repository.RedisConversationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConversationService {

    private final RedisConversationRepository redisConversationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public String create(String usernameFrom, String usernameTo) {
        RedisConversation redisConversation = new RedisConversation(usernameFrom, usernameTo);
        redisConversationRepository.save(redisConversation);
        //we notify both users to subscribe to chis conversation
        messagingTemplate.convertAndSend(TopicConstants.NEW_CONVERSATION_TOPIC + usernameTo, redisConversation);
        messagingTemplate.convertAndSend(TopicConstants.NEW_CONVERSATION_TOPIC + usernameFrom, redisConversation);
        return redisConversation.getId();
    }

    public List<RedisConversation> getAllByUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<RedisConversation> allConversations = StreamSupport.stream(
                redisConversationRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
        return allConversations.stream()
                .filter(conversation -> conversation != null &&
                        (conversation.getUsernameFrom().equals(username) ||
                                conversation.getUsernameTo().equals(username)))
                .collect(Collectors.toList());
    }

    public List<MessageDTO> getMessagesByConversationId(String conversationId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        RedisConversation conversation = redisConversationRepository.findById(conversationId).orElse(null);
        if (conversation != null && (conversation.getUsernameTo().equals(username) || conversation.getUsernameFrom().equals(username))){
            conversation.setIsOpened(true);
            redisConversationRepository.save(conversation);
            return conversation.getMessages();
        }else return Collections.emptyList();
    }
}