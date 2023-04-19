package com.example.rentify.controller;

import com.example.rentify.dto.MessageDTO;
import com.example.rentify.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

    private final ChannelTopic topic;
    private final MessageService messageService;
    private final RedisTemplate<String, Object> redisTemplate;

    // to enable multiple conversations where each user can only
    // talk to one other user, we can use a single Redis channel
    // for all conversations. However, we need to use keys identify
    // each conversation and ensure that users only communicate with
    // the intended recipient.With the conversation ID as the key
    // and the details of the users involved in the conversation
    // when a user sends a message, we will use conversation ID to
    // retrieve the details of the conversation and ensure that the
    // message is only sent to the intended recipient.

    @PostMapping("send") // POST http://localhost:8080/api/messages/send
    public ResponseEntity<String> publish(@RequestBody MessageDTO message) {
        if (message.getId() != null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        log.info("Adding new message : {} ", message);
        messageService.store(message);
        //channel on which we send message,and second argument is msg. itself
        redisTemplate.convertAndSend(topic.getTopic(), message.toString());
        return new ResponseEntity<>("Message sent!", HttpStatus.CREATED);
    }

    // GET http://localhost:8080/api/messages/get-by-conversation-id/9?page=0&size=5
    @GetMapping("get-by-sender-id/{id}") //DODAJ SORTIRANJE NA SAMI PAGE ILI PO NAJMLADJOJ PORUCI ILI po id-evima
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable Integer id, Pageable pageable) {
        List<MessageDTO> messages = messageService.getMessages(id, pageable);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

//    @MessageMapping("/chat.sendMessage")//
//    @SendTo("/topic/javainuse") //send message to this topic
//    public ChatMessage publishMessage(ChatMessage a) {
//        return a;
//    }
}