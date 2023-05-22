package com.example.rentify.controller;

import com.example.rentify.dto.UserDTO;
import com.example.rentify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    //i za login ti povlaci sve role nesto optimizuj i to

    //dodaj u jwt filter da gleda je li taj korisnika kativan pri svakom zahtjevu
    //i popravi AuthContrroler tj makni ono sto si dodao

    //spsiak svih stanova koji nisu odobreni

    //kada klijent posalje stan moras ga odobriti (posalji notifikaciju preko redisa)

    //brisanje nekog stana ako korisnik doda nesto u medjuvremnu?

    //brisanje losih komentara

    //spisak svih korisnika
    @GetMapping("all-users") //GET http://localhost:8080/api/admin/all-users?page=0&size=5
    public ResponseEntity<List<UserDTO>> findAll(Pageable pageable) {
        //EDITUJ OVO NE VRACE OPTIMALNO!
        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }

    //activiraj usera
    @PutMapping("user/{id}")
    public ResponseEntity<Void> activateUser(@PathVariable Integer id) {
        userService.activateById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //delete user
    @DeleteMapping("user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
