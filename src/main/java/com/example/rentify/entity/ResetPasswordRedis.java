package com.example.rentify.entity;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "ResetPassword", timeToLive = 900)
public class ResetPasswordRedis implements Serializable {
    @Id
    private String id; //id is user's mail
    private String generatedCode;
}
