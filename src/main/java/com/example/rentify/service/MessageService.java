package com.example.rentify.service;

import com.example.rentify.dto.MessageDTO;
import com.example.rentify.entity.Message;
import com.example.rentify.mapper.MessageMapper;
import com.example.rentify.repository.ConversationRepository;
import com.example.rentify.repository.MessageRepository;
import com.example.rentify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageMapper messageMapper;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;

    @Cacheable(value = "messages", key = "#id + ':' + #pageable.toString()")
    public List<MessageDTO> getMessages(Integer id, Pageable pageable) {
        Page<Message> messagesPage = messageRepository.findMessagesForConversationId(id, pageable);
        return messagesPage.hasContent() ?
                messageMapper.toDTOList(messagesPage.getContent()) :
                Collections.emptyList();
    }
}