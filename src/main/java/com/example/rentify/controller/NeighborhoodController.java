package com.example.rentify.controller;

import com.example.rentify.dto.NeighborhoodDTO;
import com.example.rentify.service.NeighborhoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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

    //kada u filter sekciji izaberemo grad da mozemo dodatno vrsiti pretragu naselja za taj grad
    @GetMapping // GET http://localhost:8080/api/neighborhood?page=0&size=4&cityName=Podg&neighborhoodName=Star
    public ResponseEntity<List<NeighborhoodDTO>> findByCityNameAndNeighborhoodName(
            @RequestParam("cityName") String cityName,
            @RequestParam("neighborhoodName") String neighborhoodName, Pageable pageable) {
        List<NeighborhoodDTO> neighborhoods =
                neighborhoodService.findByCityAndNeighborhood(cityName, neighborhoodName, pageable);
        log.info("Neighborhoods : {} ", neighborhoods);
        return new ResponseEntity<>(neighborhoods, HttpStatus.OK);
    }
}