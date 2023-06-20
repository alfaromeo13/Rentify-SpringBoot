package com.example.rentify.service;

import com.example.rentify.repository.PropertyTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropertyTypeService {
    private final PropertyTypeRepository propertyTypeRepository;

    public boolean existsByName(String name) {
        return propertyTypeRepository.existsByName(name);
    }
}