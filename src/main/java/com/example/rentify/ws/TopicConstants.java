package com.example.rentify.ws;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TopicConstants {
    //na topice slusaju korisnici i kada se nesto posalje na njih oni primaju te poruke na frontu
    public static final String CREATED_APARTMENT_TOPIC = "/topic/created-apartment";
    public static final String NOTIFICATION_TOPIC = "/topic/notification";
    public static final String CONVERSATION_TOPIC = "/topic/conversation/";
    public static final String NEW_CONVERSATION_TOPIC = "/topic/incoming-conversation/";
}