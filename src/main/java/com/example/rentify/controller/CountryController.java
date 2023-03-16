package com.example.rentify.controller;

import com.example.rentify.dto.CountryDTO;
import com.example.rentify.entity.Country;
import com.example.rentify.projections.CountryIdAndShortCodeProjection;
import com.example.rentify.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/country")
public class CountryController {

    private final CountryService countryService;

    @GetMapping("allu") //tozla bre
    public ResponseEntity<List<CountryIdAndShortCodeProjection>> findAll(Pageable pageable) {
        List<CountryIdAndShortCodeProjection> countries = countryService.findAllPa(pageable);
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    //GET -> http://localhost:8080/api/country?page=0&size=2
    //GET -> http://localhost:8080/api/country?page=0&size=2&sort=name,asc
    //ne mozemo sortirati ovdje po vise kolona sort je samo po 1 koloni
    @GetMapping
    public ResponseEntity<Page<Country>> findAllWithPageable(Pageable pageable) {
        //kako prihvatamo elemente sa strane klijenta?dovoljno je samo ovo da napisemo Pageable pageable
        //recimo da saljemo preko postmana.U ovom pageablu ce se mapirati parametri posalti tj page i size
        //ako zelimo dodati jos i srot dodajemo &sort=nazivKolonePoKojojSortiramo,asc ili desc
        Page<Country> countryPage = countryService.findAllWithPageable(pageable);
        return new ResponseEntity<>(countryPage, HttpStatus.OK);
    }

    @GetMapping("/pageable")
    public ResponseEntity<List<Country>> findAllPageable(Pageable pageable) {
        List<Country> countries = countryService.findAllPageable(pageable);
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> store(@RequestBody CountryDTO countryDTO) {
        //da bi primili podatke iz bodija tj mapirali ih koristimo @RequestBody
        if (countryDTO.getId() != null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        countryService.save(countryDTO);
        //mi preko ovoga mozemo da radimo i PUT tj update ako korisnik prslijedi i id
        //u JSONU ali nije po dobroj praksi vec se pravi posebna metoda kontrolera za
        // PUT glagol
        return new ResponseEntity<>(HttpStatus.CREATED);//201
    }

    @PutMapping("/{id}") //PUT http://localhost:8080/api/country/6 i predamo body
    public ResponseEntity<Void> update(@PathVariable Integer id,
                                       @RequestBody CountryDTO countryDTO) {
        countryService.update(id, countryDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        countryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}