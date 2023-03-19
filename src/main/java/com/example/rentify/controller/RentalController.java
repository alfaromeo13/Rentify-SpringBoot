package com.example.rentify.controller;

import com.example.rentify.service.RentalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rental")
public class RentalController {

    private final RentalService rentalService;

//    @PutMapping("{id}")
//    public ResponseEntity<Void> update(@PathVariable("id") Integer id, @RequestBody RentalDTO rentalDTO) {
//        //...
//    }
}
