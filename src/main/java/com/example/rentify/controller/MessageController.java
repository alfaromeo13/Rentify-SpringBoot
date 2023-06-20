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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

    //SAMO ZA PRIKAZ PORUKA

    private final MessageService messageService;
    private final RedisTemplate<String, Object> redisTemplate;

    @PostMapping("send") // POST http://localhost:8080/api/messages/send
    public ResponseEntity<String> publish(@RequestBody MessageDTO message) {
//        if (message.getId() != null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        log.info("Adding new message : {} ", message);
   //     messageService.store(message);
        return new ResponseEntity<>("Message sent!", HttpStatus.CREATED);
    }

    // GET http://localhost:8080/api/messages/get-by-conversation-id/9?page=0&size=5
    @GetMapping("get-by-sender-id/{id}") //DODAJ SORTIRANJE NA SAMI PAGE ILI PO NAJMLADJOJ PORUCI ILI po id-evima
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable Integer id, Pageable pageable) {
        List<MessageDTO> messages = messageService.getMessages(id, pageable);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }


}