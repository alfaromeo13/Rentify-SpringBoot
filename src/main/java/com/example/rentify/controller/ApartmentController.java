package com.example.rentify.controller;

import com.example.rentify.dto.ApartmentDTO;
import com.example.rentify.dto.IncomingImagesDTO;
import com.example.rentify.search.ApartmentSearch;
import com.example.rentify.service.ApartmentService;
import com.example.rentify.service.ImageService;
import com.example.rentify.validator.ApartmentValidator;
import com.example.rentify.validator.ImageInsertValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/apartment")
public class ApartmentController {

    private final ImageService imageService;
    private final ApartmentValidator aptValidator;
    private final ApartmentService apartmentService;
    private final ImageInsertValidator imgValidator;

    //http://localhost:8080/api/apartment/pageable-search?page=0&size=2&cityName=New York&minPrice=1000&maxPrice=5000
    //http://localhost:8080/api/apartment/pageable-search?page=0&size=2&availableFrom=20-04-2023&availableTo=23-04-2023
    //http://localhost:8080/api/apartment/pageable-search?type=apartment,condo&period=day,month
    @GetMapping("pageable-search")
    public ResponseEntity<List<ApartmentDTO>> search(ApartmentSearch params, Pageable pageable) {
        List<ApartmentDTO> apartments = apartmentService.search(pageable, params);
        return new ResponseEntity<>(apartments, HttpStatus.OK);
    }

    @PostMapping
    @SneakyThrows //POST http://localhost:8080/api/apartment/
    public ResponseEntity<Void> multipartHandler(@RequestParam MultipartFile[] images, @RequestParam String payload) {
        ApartmentDTO apartment = new ObjectMapper().readValue(payload, ApartmentDTO.class);
        IncomingImagesDTO imagesDTO = new IncomingImagesDTO(null, images);
        ValidationUtils.invokeValidator(imgValidator, imagesDTO, new BeanPropertyBindingResult(imagesDTO, "imagesDTO"));
        ValidationUtils.invokeValidator(aptValidator, apartment, new BeanPropertyBindingResult(apartment, "apartment"));
        apartment.setImages(imageService.saveToFs(images));
        apartmentService.save(apartment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @SneakyThrows
    @PutMapping("{id}")
    @PreAuthorize("@apartmentAuth.hasPermission(#id)") //PUT http://localhost:8080/api/apartment/2
    public ResponseEntity<Void> update(
            @PathVariable Integer id, @RequestParam String payload,
            @RequestParam(required = false) MultipartFile[] newImages,
            @RequestParam(required = false) List<Integer> deletedImages) {
        IncomingImagesDTO imagesDTO = new IncomingImagesDTO(id, newImages);
        ApartmentDTO apartment = new ObjectMapper().readValue(payload, ApartmentDTO.class);
        ValidationUtils.invokeValidator(imgValidator, imagesDTO, new BeanPropertyBindingResult(imagesDTO, "imagesDTO"));
        ValidationUtils.invokeValidator(aptValidator, apartment, new BeanPropertyBindingResult(apartment, "apartment"));
        apartmentService.update(id, apartment, imagesDTO, deletedImages);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("reverse-state/{id}") //PUT http://localhost:8080/api/apartment/2
    @PreAuthorize("@apartmentAuth.hasPermission(#id)")
    public ResponseEntity<Void> enableOrDisable(@PathVariable Integer id) {
        apartmentService.reverseState(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*JSON for POST or PUT
    {
    "title": "test123",
    "description": "neki opis",
    "sqMeters": 123,
    "price": 122,
    "numOfBedrooms": 3,
    "numOfBathrooms": 2,
    "number": "+3838676878",
    "address": {
        "street": "Broadway 789",
        "x": 123123123,
        "y": 123123123,
        "neighborhood": {
            "id": 1
        }
    },
    "period": {
        "name": "month"
    },
    "propertyType":{
        "name": "Condo"
    },
    "apartmentAttributes": [
        {
            "attribute": {
                "name": "Balcony"
            },
            "attributeValue": "Yes"
        },
        {
            "attribute": {
                "name": "Air Conditioning"
            },
            "attributeValue": "Yes"
        },
        {
            "attribute": {
                "name": "Parking"
            },
            "attributeValue": "No"
        }
      ]
    }
*/
}