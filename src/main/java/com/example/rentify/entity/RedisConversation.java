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
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("RedisMessage")
public class RedisConversation implements Serializable {
    //definisemo strukturu konverzacije koju cuvamo
    // unutar redisa. Redis je key value struktura
    @Id private String id;
    private String username1;
    private String username2;
    private MessageDTO message;
}