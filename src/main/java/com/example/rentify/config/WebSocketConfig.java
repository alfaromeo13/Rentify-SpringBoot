package com.example.rentify.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/redis-chat") //front otvara komunikaciju na redis chat preko ovoga
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");//na topic prima
        config.setApplicationDestinationPrefixes("/app"); //poruke saljem preko ove rute tj front salje preko nje
        //dakle ja je saljem na app a drugi je prima na topic

        // subscribe (slusam na nove poruke): /topic/conversation-with/{username}
        // send messages: /app/receive/{from-username}/{to-username}

        // 1. Supskripcija na topic (Jovan user): /topic/conversation-with/heril
        // 2. Supskripcija na topic (Heril user): /topic/conversation-with/jovan
        // 3. Jovan: "Zdravo!" saljem na  (/app/receive/jovan/heril)
        // 4. Upis u redis
        // 5. Slanje preko soketa frontu na odredjeni topic: /topic/conversation-with/jovan
        // 5*. Ako korisnik ne slusa na topic (vrlo moguce) prebaci na drugi topic
        // 6. Heril: "Cao!" (ovdje sam vec subscribe-ovan zato sto pokusavam da posaljem poruku): /app/receive/heril/jovan
        // 7. Upis u redis
        // 8. Slanje preko soketa frontu na odredjeni topic: /topic/conversation-with/heril
    }
}