package com.example.rentify.controller;

import com.example.rentify.service.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/status")
public class StatusController {

    private final StatusService statusService;

    //koristimo kada budemo dodavali nedje status nad stanom(pa da izaberemo akciju nad
    //stanom od ponudjenih i sl.)
    @GetMapping //GET http://localhost:8080/api/status/
    public ResponseEntity<List<String>> findAll() {
        return new ResponseEntity<>(statusService.find(), HttpStatus.OK);
    }
}
