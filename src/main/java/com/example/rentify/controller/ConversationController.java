package com.example.rentify.controller;

import com.example.rentify.dto.ConversationDTO;
import com.example.rentify.service.ConversationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/conversation")
public class ConversationController {

    private final ConversationService conversationService;

    //get conversations for user by his id(when we login to app)
    @GetMapping("for-user/{id}")// GET http://localhost:8080/api/conversation/by-user/id=5?page=0&size=5
    public ResponseEntity<List<ConversationDTO>> findAll(@PathVariable Integer id, Pageable pageable) {
        List<ConversationDTO> conversations = conversationService.findByUserId(id, pageable);
        return new ResponseEntity<>(conversations, HttpStatus.OK);
    }

    //find conversation by specific receiver username(when we search for chat..)
    // GET http://localhost:8080/api/conversation/by-username/JovanVukovic?page=0&size=5
    @GetMapping("by-username/{username}")
    public ResponseEntity<List<ConversationDTO>> findAll(@PathVariable String username, Pageable pageable) {
        List<ConversationDTO> conversation = conversationService.findByUsername(username, pageable);
        return new ResponseEntity<>(conversation, HttpStatus.OK);
    }

    //when we close conversations(remove it from history)
    @DeleteMapping("{id}") //DELETE http://localhost:8080/api/conversation/2
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Deleting conversation with id: {} ", id);
        boolean deleted = conversationService.delete(id);
        return new ResponseEntity<>(deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}