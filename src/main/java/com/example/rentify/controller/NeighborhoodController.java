package com.example.rentify.controller;

import com.example.rentify.dto.CountryDTO;
import com.example.rentify.dto.NeighborhoodDTO;
import com.example.rentify.service.NeighborhoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/neighborhood")
public class NeighborhoodController {

    private final NeighborhoodService neighborhoodService;

    // GET http://localhost:8080/api/neighborhood?cityName=Podg&neighborhoodName=Star
    @GetMapping
    public ResponseEntity<List<NeighborhoodDTO>> findByCityNameAndNeighborhoodName(
            @RequestParam("cityName") String cityName, @RequestParam("neighborhoodName") String neighborhoodName) {
        List<NeighborhoodDTO> neighborhoods = neighborhoodService.findByCityAndNeighborhood(cityName, neighborhoodName);
        log.info("Neighborhoods : {} ", neighborhoods);
        return new ResponseEntity<>(neighborhoods, HttpStatus.OK);
    }
}
