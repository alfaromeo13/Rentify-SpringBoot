package com.example.rentify.controller;

import com.example.rentify.dto.ApartmentDTO;
import com.example.rentify.entity.Apartment;
import com.example.rentify.search.ApartmentSearch;
import com.example.rentify.service.ApartmentService;
import com.example.rentify.specs.ApartmentSearchSpecification;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/apartment")
public class ApartmentController {

    private final ApartmentService apartmentService;
    private final Logger LOGGER = LoggerFactory.getLogger(ApartmentController.class);

    //GET http://localhost:8080/api/apartment/search?numOfBathrooms=2&price=2000&addressStreet=Broadway
    @GetMapping(value = "search")  //search?minPrice=500&maxPrice=1000&minRooms=2&maxRooms=3&page=0&size=10
    public ResponseEntity<List<ApartmentDTO>> search(Pageable pageable, ApartmentSearch apartmentSearch) {
        //u oApartmentSearch ce se mapirti samo proslijedjeni queriji ostali ce biti null i po tome filtriramo
        //ovim imamo pristup poslatim query parametrima.Ova klasa ispod generise where dio upita
        ApartmentSearchSpecification aps = new ApartmentSearchSpecification(apartmentSearch);
        List<ApartmentDTO> apartments = apartmentService.search(pageable, aps);
        LOGGER.info("Apartments : {}", apartments);

//        https://stackoverflow.com/questions/26379522/can-i-combine-a-query-definition-with-a-specification-in-a-spring-data-jpa-repo?noredirect=1&lq=1

        return new ResponseEntity<>(apartments, HttpStatus.OK);
    }

//    @GetMapping(value = "{id}")
//    // "/api/product/{id}"    //da smo imali jos jednu patrh vcariablu bilo bi {}/{} a ovamo ,PathVariable pa ta druga
//    public ResponseEntity<ApartmentDTO> getById(@PathVariable Integer id) {  //ako je naziv path variable u {id} isti kao u metodi tj INteger id odnosno imaju isti naziv pa ne treba value="id"
//        ApartmentDTO apartment = apartmentService.findOneById(id);
//        return apartment != null ?
//                new ResponseEntity<>(apartment, HttpStatus.OK) ://200
//                new ResponseEntity<>(HttpStatus.NOT_FOUND);//404
//    }


//    //http://localhost:8080/api/product/by-category?category=Kat 1
//    @GetMapping(value = "by-category") // /api/product/by-category} prosljejdumeo kao query p[arametar jer nije unique
//    //mozemo dodati required false jer je po defasultu tru i ako se ne proslijedi query param bice odbijen
//    public ResponseEntity<List<ApartmentDTO>> getByCategory(@RequestParam(value = "category") String category) {
//        List<ApartmentDTO> apartments = apartmentService.findByCategory(category);
//        return new ResponseEntity<>(apartments, HttpStatus.OK); //vratice se prazna lista nece null tj vratice se []
//    }

    @GetMapping(value = "by-categories") //api/product/by-categories
    //predajmeo listu kategorijia u POSTAMN npr odajemo ...by-categpories?category=Kat 1&category=Kat 2 itd..
    public ResponseEntity<List<ApartmentDTO>> getByCategories(@RequestParam(value = "category") List<String> categories) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //get review for an aartment

}