package com.example.rentify.service;

import com.example.rentify.dto.MessageDTO;
import com.example.rentify.entity.Conversation;
import com.example.rentify.entity.Message;
import com.example.rentify.entity.User;
import com.example.rentify.mapper.MessageMapper;
import com.example.rentify.repository.ConversationRepository;
import com.example.rentify.repository.MessageRepository;
import com.example.rentify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @CacheEvict(value = "messages", allEntries = true)
    public void store(MessageDTO messageDTO) {
        Conversation conversation = new Conversation();
        User user1 = userRepository.findById
                (messageDTO.getSenderId()).orElse(null);
        User user2 = userRepository.findById
                (messageDTO.getReceiverId()).orElse(null);
        conversation.setUser1(user1);
        conversation.setUser2(user2);
        if (messageDTO.getConversationId() != null) {
            //ovdje NAPISI QVERI KOJI GLEDA IMA LI IJEDNA AKTIVNA UMJESTO OVOGA
            if (conversationRepository.existsById(messageDTO.getConversationId()))
                conversation.setId(messageDTO.getConversationId());
            else
                conversation = conversationRepository.save(conversation);
        } else
            conversation = conversationRepository.save(conversation);
        Message message = new Message();
        message.setMessage(messageDTO.getMessage());
        message.setSender(user1);
        message.setReceiver(user2);
        message.setConversation(conversation);
        messageRepository.save(message);
    }
}