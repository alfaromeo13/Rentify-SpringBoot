package com.example.rentify.controller;

import com.example.rentify.dto.ImagePreview;
import com.example.rentify.dto.MessageDTO;
import com.example.rentify.entity.RedisConversation;
import com.example.rentify.service.ImageService;
import com.example.rentify.ws.TopicConstants;
import com.example.rentify.ws.repository.RedisConversationRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageController {

    private final ImageService imageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final RedisConversationRepository redisConversationRepository;

    @GetMapping("preview/{id}") //testing preview api
    public ResponseEntity<ImagePreview> preview(@PathVariable Integer id) throws IOException {
        ImagePreview imagePreview = imageService.getEncodedById(id);
        return ResponseEntity.ok(imagePreview);
    }

    @SneakyThrows
    @PostMapping("{conversationId}")
    public ResponseEntity<Void> multipartHandler(@RequestParam MultipartFile[] images, @PathVariable String conversationId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        RedisConversation conversation = redisConversationRepository.findById(conversationId).orElseThrow(()
                -> new EntityNotFoundException("Conversation not exists. Id: " + conversationId));
        for(MultipartFile image : images){
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setSender(username);
            messageDTO.setTimestamp(new Date());
            String filename = image.getOriginalFilename()+ "";
            messageDTO.setMessage("data:image/" + filename.substring(filename.lastIndexOf(".") + 1)
                    + ";base64," + Base64.getEncoder().encodeToString(image.getBytes()));
            conversation.appendMessage(messageDTO);
            messagingTemplate.convertAndSend(TopicConstants.CONVERSATION_TOPIC + conversationId, messageDTO);
        }
        conversation.setIsOpened(false);
        redisConversationRepository.save(conversation);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}