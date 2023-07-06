package com.example.rentify.repository;

import com.example.rentify.RentifyApplication;
import com.example.rentify.dto.MessageDTO;
import com.example.rentify.entity.RedisConversation;
import com.example.rentify.service.ConversationService;
import com.example.rentify.ws.repository.RedisConversationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RentifyApplication.class)
class RedisConversationRepositoryTest {

    @Autowired
    private RedisConversationRepository redisConversationRepository;

    @Autowired
    private ConversationService conversationService;


    @Test
    void shouldCreateRedisConversion() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage("Cao, sto se radi?");
        messageDTO.setTimestamp(new Date());

        RedisConversation conversation = new RedisConversation();
        conversation.setUsernameFrom("bobsmith");
        conversation.setUsernameTo("johndoe");
        conversation.setMessages(List.of(messageDTO));

        RedisConversation storedConversation = redisConversationRepository.save(conversation);
        assertThat(storedConversation).isNotNull();
        assertThat(storedConversation.getId()).isNotNull();
    }

    @Test
    void shouldRetrieveConversationById() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage("Hey?");
        messageDTO.setTimestamp(new Date());

        RedisConversation conversation = new RedisConversation();
        conversation.setUsernameFrom("johndoe");
        conversation.setUsernameTo("bobsmith");
        conversation.setMessages(List.of(messageDTO));

        redisConversationRepository.save(conversation);

        RedisConversation existingConversation = redisConversationRepository
                .findById(conversation.getId())
                .orElseThrow(EntityNotFoundException::new);

        assertThat(existingConversation).isNotNull();
        assertThat(existingConversation.getId()).isEqualTo(conversation.getId());
    }

    @Test
    void shouldRetrieveConversationByUsernameFromOrUsernameTo() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage("What's up bro?");
        messageDTO.setTimestamp(new Date());

        RedisConversation conversation = new RedisConversation();
        conversation.setUsernameFrom("johndoe");
        conversation.setUsernameTo("bobsmith");
        conversation.setMessages(List.of(messageDTO));
        redisConversationRepository.save(conversation);

        List<RedisConversation> conversations = conversationService.getAllByUsername("johndoe");
        assertThat(conversations).isNotNull().isNotEmpty();
        assertThat(conversations.iterator().next().getMessages()).isNotEmpty();
    }
}