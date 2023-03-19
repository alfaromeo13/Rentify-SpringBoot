package com.example.rentify.controller;

import com.example.rentify.dto.ReviewApartmentDTO;
import com.example.rentify.dto.ReviewDTO;
import com.example.rentify.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    /*
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

    @PostMapping //POST http://localhost:8080/api/review/
    public ResponseEntity<Void> insert(@RequestBody ReviewApartmentDTO reviewApartmentDTO) {
        if (reviewApartmentDTO.getId() != null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        log.info("Adding review : {} ", reviewApartmentDTO);
        reviewService.save(reviewApartmentDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @DeleteMapping("{id}") //DELETE http://localhost:8080/api/review/55
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Deleting review with id: {} ", id);
        boolean deleted = reviewService.delete(id);
        return new ResponseEntity<>(deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /*
        {
        "grade":1,
        "comment":"kkkkkkk",
        "apartmentId":4,
        "isActive":1,
            "user" : {
                "id":12,
                "firstName":"Marko",
                "lastName":"Vukovic",
                "email":"bula20@gmail.com",
                "username":"bula12345!"
            }
        }
 */

    @PutMapping("{id}") //PUT http://localhost:8080/api/review/55 -> when we edit a review
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody ReviewApartmentDTO reviewApartmentDTO) {
        log.info("Edited review with id : {} ", id);
        boolean updated = reviewService.update(id, reviewApartmentDTO);
        return new ResponseEntity<>(updated ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
