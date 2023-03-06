package com.example.rentify.controller;

import com.example.rentify.dto.ApartmentDTO;
import com.example.rentify.search.ApartmentSearch;
import com.example.rentify.service.ApartmentService;
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


    //http://localhost:8080/api/product/
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

    @GetMapping(value="searchhhEx") //name=""&category=""&price""
    public ResponseEntity<List<ApartmentDTO>> searchByMany(@RequestParam Map<String, Object> parameters) {
        //moramo praviti precizan skup query parametara koje mapiramo pa ce nam trebati klasa za ovo pa pisemo ovo ispoid

//        NAPRAVICEMO KLASU KOJA PREDTVALJA QUERY PARAMETRE
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value="search")
    public ResponseEntity<List<ApartmentDTO>> search(ApartmentSearch apartmentSearch) {//u ApartmentSearch ce se mapirati svaki
        //query parametar ako je definisan.Razlika izmedju ovakvog definisanja request param je sto ovdje vriejdnosti
        //mogu biti nulli i bice samo mnapirane vrijednosti koje proslijedimo ovdje
        //svi dodatni quey parametri ce biti ignorisdani
        LOGGER.info("Apartment search: {}",apartmentSearch);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}