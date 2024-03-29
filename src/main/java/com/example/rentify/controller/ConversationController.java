package com.example.rentify.controller;

import com.example.rentify.dto.CreateConversationDTO;
import com.example.rentify.dto.MessageDTO;
import com.example.rentify.entity.RedisConversation;
import com.example.rentify.service.ConversationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    @PostMapping
    public ResponseEntity<Map<String, String>> create(@RequestBody CreateConversationDTO conversation) {
        String conversationId = conversationService.create(conversation);  //we return conversationId and we subscribe to it on front
        return new ResponseEntity<>(Map.of("conversationId", conversationId), HttpStatus.CREATED);
    }

    @GetMapping("by-user") //send back conversations based on username inside token
    public ResponseEntity<List<RedisConversation>> allForUser() {
        List<RedisConversation> conversations = conversationService.getAllByUsername();
        return new ResponseEntity<>(conversations, HttpStatus.OK);
    }

    @GetMapping("{id}/messages")
    public ResponseEntity<List<MessageDTO>> getMessagesByConversationId(@PathVariable String id) {
        List<MessageDTO> messages = conversationService.getMessagesByConversationId(id);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}