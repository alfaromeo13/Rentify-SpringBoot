package com.example.rentify.repository;

import com.example.rentify.RentifyApplication;
import com.example.rentify.dto.MessageDTO;
import com.example.rentify.entity.RedisConversation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RentifyApplication.class)
class RedisConversationRepositoryTest {

    @Autowired
    private RedisConversationRepository redisConversationRepository;


    @Test
    void shouldCreateRedisConversion() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage("Poruka1");
        messageDTO.setTimestamp(new Date());

        RedisConversation conversation = new RedisConversation();
        conversation.setUsername1("jovan");
        conversation.setUsername2("heril");
        conversation.setMessage(messageDTO);

        RedisConversation storedConversation = redisConversationRepository.save(conversation);
        assertThat(storedConversation).isNotNull();
        assertThat(storedConversation.getId()).isNotNull();
    }
}