package com.example.rentify.service;

import com.example.rentify.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;

    @Cacheable("statuses")
    public List<String> find() {
        log.info("Cache miss..Getting data from database.");
        return statusRepository.findAllStatuses();
    }
}