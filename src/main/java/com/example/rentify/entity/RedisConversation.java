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
public class RedisConversation implements Serializable { //Redis je key value struktura
    //definisemo strukturu konverzacije koju cuvamo unutar redisa
    @Id
    private String id;
    //usernameFrom i usernameTo je bitno samo zbog islistavanja konverzacija da bi za odredjenog usera kad se loguje
    //nasli njegove konverzacije ( ako mu je ime sendera ili receivera )
    private String usernameFrom; // johndoe, heril, jovan
    private String usernameTo; // heril, johndoe
    private Date createdAt;
    private Boolean isOpened;
    private List<MessageDTO> messages = new ArrayList<>();//poruke imaju ko je posiljaoc koje poruke

    public RedisConversation(String usernameFrom, String usernameTo) {
        this.usernameFrom = usernameFrom;
        this.usernameTo = usernameTo;
        this.createdAt = new Date();
    }

    public void appendMessage(MessageDTO messageDTO) {
        this.messages.add(messageDTO);
    }
}