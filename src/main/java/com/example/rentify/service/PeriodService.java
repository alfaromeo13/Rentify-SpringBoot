package com.example.rentify.service;

import com.example.rentify.repository.PeriodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PeriodService {
    private final PeriodRepository periodRepository;

    public boolean existsByName(String name) {
        return periodRepository.existsByName(name);
    }
}