package com.example.rentify.service;

import com.example.rentify.dto.MessageDTO;
import com.example.rentify.entity.RedisConversation;
import com.example.rentify.ws.repository.RedisConversationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class ConversationService {

    private final RedisConversationRepository redisConversationRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, Long, RedisConversation> hashOperations;

    public ConversationService(RedisTemplate<String, Object> redisTemplate,
                               RedisConversationRepository redisConversationRepository) {
        this.redisConversationRepository = redisConversationRepository;
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }


    public String create(String usernameFrom, String usernameTo) {
        RedisConversation redisConversation = new RedisConversation(usernameFrom, usernameTo);
        redisConversationRepository.save(redisConversation);

        return redisConversation.getId();
    }

    public List<RedisConversation> getAllByUsername(String username) {
        List<RedisConversation> allConversations = StreamSupport.stream(
                redisConversationRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());

        return allConversations.stream()
                .filter(conversation
                        -> conversation.getUsernameFrom().equals(username) || conversation.getUsernameTo().equals(username))
                .collect(Collectors.toList());
    }

    public List<MessageDTO> getMessagesByConversationId(String conversationId) {
        RedisConversation conversation = redisConversationRepository
                .findById(conversationId)
                .orElseThrow(()
                        -> new EntityNotFoundException("Conversation with id " + conversationId + " not exists!"));

        return conversation.getMessages();
    }
}