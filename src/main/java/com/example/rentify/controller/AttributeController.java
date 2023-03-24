package com.example.rentify.controller;

import com.example.rentify.dto.AttributeDTO;
import com.example.rentify.service.AttributeService;
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
@RequestMapping("/api/attribute")
public class AttributeController {

    private final AttributeService attributeService;

    @GetMapping //GET http://localhost:8080/api/attribute/
    public ResponseEntity<List<AttributeDTO>> findAll() {
        List<AttributeDTO> attributes = attributeService.findAll();
        log.info("Attributes : {} ", attributes);
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }
}