package com.example.rentify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@SpringBootApplication
@EnableRedisRepositories(basePackages = {"com.example.rentify.ws.repository","com.example.rentify.repository"})
public class RentifyApplication {
    public static void main(String[] args) {
        SpringApplication.run(RentifyApplication.class, args);
    }
}