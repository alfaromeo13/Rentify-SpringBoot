package com.example.rentify.controller;

import com.example.rentify.dto.ApartmentDTO;
import com.example.rentify.entity.Apartment;
import com.example.rentify.search.ApartmentSearch;
import com.example.rentify.service.ApartmentService;
import com.example.rentify.specs.ApartmentSearchSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/apartment")
public class ApartmentController {
    //handling all requests for apartment entity
    @Autowired
    private ApartmentService apartmentService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentController.class);


    //http://localhost:8080/api/apartment/
    @GetMapping  // /api/apartment
    public ResponseEntity<List<ApartmentDTO>> getAll() {
        List<ApartmentDTO> apartments = apartmentService.getAll();
        return new ResponseEntity<>(apartments, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    // "/api/product/{id}"    //da smo imali jos jednu patrh vcariablu bilo bi {}/{} a ovamo ,PathVariable pa ta druga
    public ResponseEntity<ApartmentDTO> getById(@PathVariable Integer id) {  //ako je naziv path variable u {id} isti kao u metodi tj INteger id odnosno imaju isti naziv pa ne treba value="id"
        ApartmentDTO apartment = apartmentService.findOneById(id);
        return apartment != null ?
                new ResponseEntity<>(apartment, HttpStatus.OK) ://200
                new ResponseEntity<>(HttpStatus.NOT_FOUND);//404
    }


    //http://localhost:8080/api/product/by-category?category=Kat 1
    @GetMapping(value = "by-category") // /api/product/by-category} prosljejdumeo kao query p[arametar jer nije unique
    //mozemo dodati required false jer je po defasultu tru i ako se ne proslijedi query param bice odbijen
    public ResponseEntity<List<ApartmentDTO>> getByCategory(@RequestParam(value = "category") String category) {
        List<ApartmentDTO> apartments = apartmentService.findByCategory(category);
        return new ResponseEntity<>(apartments, HttpStatus.OK); //vratice se prazna lista nece null tj vratice se []
    }

    @GetMapping(value = "by-categories") //api/product/by-categories
    //predajmeo listu kategorijia u POSTAMN npr odajemo ...by-categpories?category=Kat 1&category=Kat 2 itd..
    public ResponseEntity<List<ApartmentDTO>> getByCategories(@RequestParam(value = "category") List<String> categories) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //http://localhost:8080/api/apartment/search?numOfBathrooms=2&price=2000&addressStreet=Broadway
    @GetMapping(value = "search")
    public ResponseEntity<List<Apartment>> search(ApartmentSearch apartmentSearch) {//u ApartmentSearch ce se mapirati svaki
        //query parametar ako je definisan.Razlika izmedju ovakvog definisanja request param je sto ovdje vriejdnosti
        //mogu biti nulli i bice samo mnapirane vrijednosti koje proslijedimo ovdje
        //svi dodatni quey parametri ce biti ignorisdani

        //ovim imamo pristup poslatim query parametrima
        ApartmentSearchSpecification apartmentSearchSpecification = new ApartmentSearchSpecification(apartmentSearch);
        //da bi pzoavli ovu specvifikaciju predajemo je servisnom sloju a ApartmentSearch ne predajemo jer je to klasa
        //koju smo prosliejdili do search specifikacije.Nama treba sama Search specifikacija tj where dio upita
        List<Apartment> apartments = apartmentService.search(apartmentSearchSpecification);
//        LOGGER.info("Apartment search: {}",apartmentSearch);
        return new ResponseEntity<>(apartments, HttpStatus.OK);
    }

}