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

    //vrati sve korisnike
    @GetMapping("all-users") //GET http://localhost:8080/api/admin/all-users?page=0&size=5
    public ResponseEntity<List<UserDTO>> findAll(Pageable pageable) {
        List<UserDTO> users = userService.findAll(pageable);
        log.info("Users with page : {} ", users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
