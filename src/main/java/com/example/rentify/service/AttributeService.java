package com.example.rentify.service;

import com.example.rentify.dto.AttributeDTO;
import com.example.rentify.mapper.AttributeMapper;
import com.example.rentify.repository.AttributeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttributeService {

    private final AttributeMapper attributeMapper;
    private final AttributeRepository attributeRepository;

    @Cacheable("attributes")
    public List<AttributeDTO> findAll() {
        return attributeMapper.toDTOList(attributeRepository.findAll());
    }
}
