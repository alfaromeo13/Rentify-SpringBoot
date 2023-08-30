package com.example.rentify.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker //ova anotacija otvara sokete
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry //ruta na koju front OTVARA komunikaciju (samo za CONNECT operaciju)
            .addEndpoint("/websocket/subscribe")
            .setAllowedOrigins("http://localhost:4200") // TODO: eksternalizovati... (YML)
            .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); //na topic primamo poruke
        config.setApplicationDestinationPrefixes("/app"); // front salje poruke na /app/..
    }
}