package com.example.rentify.controller;

import com.example.rentify.dto.ApartmentDTO;
import com.example.rentify.search.ApartmentSearch;
import com.example.rentify.service.ApartmentService;
import com.example.rentify.specs.ApartmentSearchSpecification;
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
@RequestMapping("/api/apartment")
public class ApartmentController {

    private final ApartmentService apartmentService;

    //GET http://localhost:8080/api/apartment/pageable-search?page=0&size=4&sort=title,asc&cityName=New York&minPrice=1100&maxPrice=4900
    //or GET http://localhost:8080/api/apartment/pageable-search?page=0&size=2&cityName=New York&addressStreet=Broadway
    @GetMapping("pageable-search")
    public ResponseEntity<List<ApartmentDTO>> search(Pageable pageable, ApartmentSearch params) {
        List<ApartmentDTO> apartments = apartmentService.search(pageable, new ApartmentSearchSpecification(params));
        return new ResponseEntity<>(apartments, HttpStatus.OK);
    }

    @PostMapping  //POST http://localhost:8080/api/apartment/
    public ResponseEntity<Void> insert(@RequestBody ApartmentDTO apartmentDTO) {
        if (apartmentDTO.getId() != null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        log.info("Storing new apartment: {}", apartmentDTO);
        apartmentService.save(apartmentDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}") //PUT http://localhost:8080/api/apartment/2
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody ApartmentDTO apartmentDTO) {
        log.info("Updating apartment with id : {} ", id);
        boolean updated = apartmentService.update(id, apartmentDTO);
        return new ResponseEntity<>(updated ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}") //DELETE http://localhost:8080/api/apartment/2
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Deleting apartment with id: {} ", id);
        boolean deleted = apartmentService.delete(id);
        return new ResponseEntity<>(deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /*JSON example for POST
    {
    "title":"test123",
    "description":"neki opis",
    "sqMeters":123,
    "price":122,
    "numOfBedrooms":3,
    "numOfBathrooms":2,
    "number": "+3838676878",
    "user":{
        "id":12,
        "firstName":"Marko",
        "lastName":"Vukovic",
        "email":"bula20@gmail.com",
        "username":"bula12345!"
    },
    "address":{
        "id":36,
        "street":"Broadway",
        "number":"789",
        "code":"12345"
    },
    "images":[
        {
            "id":1
        },
        {
            "id":2
        },
        {
            "id":2
        }
    ],
    "apartmentAttributes": [
            {
                "attribute": {
                    "name": "Air Conditioning"
                },
                "attributeValue": "Central"
            },
            {
                "attribute": {
                    "name": "Balcony"
                },
                "attributeValue": "Yes"
            },
            {
                "attribute": {
                    "name": "Gym"
                },
                "attributeValue": "No"
            },
            {
                "attribute": {
                    "name": "Parking"
                },
                "attributeValue": "Underground"
            },
            {
                "attribute": {
                    "name": "Swimming Pool"
                },
                "attributeValue": "Yes"
            }
        ]
}
     */
}