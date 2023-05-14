package com.example.rentify.controller;

import com.example.rentify.dto.UserDTO;
import com.example.rentify.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    //kada klijent posalje stan moras ga odobriti (poslaji na mail ako je odobren ili ne)

    //brisanje nekog stana ako korisnik doda nesto u medjuvremnu

    //ugasi korisnikov nalog(lose namjere spamovanje i sl.)

    //otvori nalog?

    //brisanje losih komentara

    //spisak svih korisnike
    @GetMapping("all-users") //GET http://localhost:8080/api/admin/all-users?page=0&size=5
    public ResponseEntity<List<UserDTO>> findAll(Pageable pageable) {
        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }
}
