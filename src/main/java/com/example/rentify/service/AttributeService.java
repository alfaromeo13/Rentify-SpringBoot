package com.example.rentify.service;

import com.example.rentify.repository.AttributeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttributeService {

    private final AttributeRepository attributeRepository;

    public boolean existsByName(String name) {
        return attributeRepository.existsByName(name);
    }
}