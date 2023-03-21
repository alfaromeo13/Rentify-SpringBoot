package com.example.rentify.controller;

import com.example.rentify.dto.ReviewDTO;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import com.example.rentify.dto.ReviewApartmentDTO;
import com.example.rentify.service.ReviewService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping //POST http://localhost:8080/api/review/
    public ResponseEntity<Void> insert(@RequestBody ReviewApartmentDTO reviewApartmentDTO) {
        if (reviewApartmentDTO.getId() != null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        log.info("Adding review : {} ", reviewApartmentDTO);
        reviewService.save(reviewApartmentDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}") //PUT http://localhost:8080/api/review/55 -> when we edit a review
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody ReviewApartmentDTO reviewApartmentDTO) {
        log.info("Edited review with id : {} ", id);
        boolean updated = reviewService.update(id, reviewApartmentDTO);
        return new ResponseEntity<>(updated ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}") //DELETE http://localhost:8080/api/review/55
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Deleting review with id: {} ", id);
        boolean deleted = reviewService.delete(id);
        return new ResponseEntity<>(deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("by-apartment-id/{id}") //GET http://localhost:8080/api/review/by-apartment-id/5?page=0&size=5
    public ResponseEntity<List<ReviewDTO>> findForApartment(@PathVariable Integer id, Pageable pageable) {
        List<ReviewDTO> reviews = reviewService.findByApartmentId(id, pageable);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
    /*
        for POST or PUT
        {
        "grade":5,
        "comment":"komentar",
        "apartmentId":5,
            "user" : {
                "id":12,
                "firstName":"Marko",
                "lastName":"Vukovic",
                "email":"bula20@gmail.com",
                "username":"bula12345!"
            }
        }
 */
}