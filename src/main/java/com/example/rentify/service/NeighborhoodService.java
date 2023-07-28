package com.example.rentify.service;

import com.example.rentify.dto.NeighborhoodDTO;
import com.example.rentify.entity.Neighborhood;
import com.example.rentify.mapper.NeighborhoodMapper;
import com.example.rentify.repository.NeighborhoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NeighborhoodService {

    private final NeighborhoodMapper neighborhoodMapper;
    private final NeighborhoodRepository neighborhoodRepository;

    @Cacheable(value = "neighborhoods", key = "{ #cityName , #neighborhoodName , #pageable.toString()}")
    public List<NeighborhoodDTO> findByCityAndNeighborhood(String cityName, String neighborhoodName, Pageable pageable) {
        log.info("Cache miss..Getting data from database.");
        Page<Neighborhood> hoods = neighborhoodRepository
                .findByCityAndNeighborhood(cityName, neighborhoodName, pageable);
        return hoods.hasContent() ? neighborhoodMapper.toDTOList(hoods.getContent()) : Collections.emptyList();
    }

    public boolean exists(Integer id){
        return neighborhoodRepository.existsById(id);
    }
}