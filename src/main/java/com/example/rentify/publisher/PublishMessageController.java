package com.example.rentify.publisher;

import com.example.rentify.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PublishMessageController {

    private final ChannelTopic topic;
    private final RedisTemplate<String, Object> redisTemplate;
    // naziv bean-a kojeg injectujemo mora da
    // bude siti kao naziv beana u config klasi
    // a taj bean dobija naziv po nazivu nejgove metode u toj klasi

    @PostMapping("api/publish") // POST http://localhost:8080/api/publish
    public String publish(@RequestBody MessageDTO message) {
        //in the first argument we specify channel name on which
        //we send message,and second argument is the message itself
        redisTemplate.convertAndSend(topic.getTopic(), message.toString());
        return "Event published!";
    }
}
