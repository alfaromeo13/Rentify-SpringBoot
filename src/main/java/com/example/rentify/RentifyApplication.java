package com.example.rentify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class RentifyApplication {
    public static void main(String[] args) {
        SpringApplication.run(RentifyApplication.class, args);
    }
}
