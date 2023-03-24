package com.example.rentify.service;

import com.example.rentify.dto.ConversationDTO;
import com.example.rentify.entity.Conversation;
import com.example.rentify.mapper.ConversationMapper;
import com.example.rentify.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationMapper conversationMapper;
    private final ConversationRepository conversationRepository;

    @Cacheable(value = "conversations", key = "#id + ':' + #pageable.toString()")
    public List<ConversationDTO> findByUserId(Integer id, Pageable pageable) {
        Page<Conversation> conversations = conversationRepository.findAllConversationsByUserId(id, pageable);
        return conversations.hasContent() ?
                conversationMapper.toDTOList(conversations.getContent()) :
                Collections.emptyList();
    }

    @Cacheable(value = "conversations-by-username", key = "#username + ':' + #pageable.toString()")
    public List<ConversationDTO> findByUsername(String username, Pageable pageable) {
        Page<Conversation> conversations = conversationRepository.findByUsername(username, pageable);
        return conversations.hasContent() ?
                conversationMapper.toDTOList(conversations.getContent()) :
                Collections.emptyList();
    }

    @Caching(evict = {@CacheEvict(cacheNames = "conversations", allEntries = true),
            @CacheEvict(cacheNames = "conversations-by-username", allEntries = true)})
    public Boolean delete(Integer id) {
        Optional<Conversation> conversationOptional = conversationRepository.findById(id);
        if (conversationOptional.isPresent()) {
            Conversation conversation = conversationOptional.get();
            conversation.setIsActive(false);
            conversationRepository.save(conversation);
            return true;
        } else return false;
    }
}