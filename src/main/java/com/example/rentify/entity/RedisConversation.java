package com.example.rentify.entity;

import com.example.rentify.dto.MessageDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "Conversation", timeToLive = 604800) // => it will expire after 7 days
public class RedisConversation implements Serializable {
    //definisemo strukturu konverzacije koju cuvamo
    // unutar redisa. Redis je key value struktura
    @Id
    private String id;
    private String usernameFrom; // johndoe, heril, jovan
    private String usernameTo; // heril, johndoe
    private List<MessageDTO> messages = new ArrayList<>();

    public RedisConversation(String usernameFrom, String usernameTo) {
        this.usernameFrom = usernameFrom;
        this.usernameTo = usernameTo;
    }

    public void appendMessage(MessageDTO messageDTO) {
        this.messages.add(messageDTO);
    }
}