package com.example.rentify.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
            .simpDestMatchers("/topic/**").permitAll()
            .simpTypeMatchers(SimpMessageType.MESSAGE, SimpMessageType.SUBSCRIBE).permitAll();

        //specifies that messages sent to destinations starting with "/topic/" are permitted for
        //all users without authentication. This is often used for broadcasting messages to subscribed clients.

        //allows messages of type MESSAGE and SUBSCRIBE to be received without authentication.
        //This means clients can send messages and subscribe to topics without being authenticated.
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
    //useful when you're dealing with cross-origin WebSocket connections, allowing connections from different domains.
}
