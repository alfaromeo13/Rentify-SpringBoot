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

    @Override//this defines where client will connect
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/redis-chat")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        //is message has /topic in url it will be routed to @MessageMapping annotaated
        //methods in controler class
        config.setApplicationDestinationPrefixes("/app");
        // when client want to send message to the server he must use this prefix
    }
}