package com.example.rentify.service;

import com.example.rentify.dto.NeighborhoodDTO;
import com.example.rentify.entity.Neighborhood;
import com.example.rentify.mapper.NeighborhoodMapper;
import com.example.rentify.repository.NeighborhoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NeighborhoodService {

    private final NeighborhoodMapper neighborhoodMapper;
    private final NeighborhoodRepository neighborhoodRepository;

    @Cacheable(value = "neighborhoods", key = "{ #cityName , #neighborhoodName }")
    public List<NeighborhoodDTO> findByCityAndNeighborhood(
            String cityName, String neighborhoodName) {
        List<Neighborhood> neighborhoods =
                neighborhoodRepository.findByCityAndNeighborhood(cityName, neighborhoodName);
        return neighborhoodMapper.toDTOList(neighborhoods);
    }
}