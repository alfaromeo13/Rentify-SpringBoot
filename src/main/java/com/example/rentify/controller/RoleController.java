package com.example.rentify.controller;

import com.example.rentify.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    @GetMapping //GET http://localhost:8080/api/role/
    public ResponseEntity<List<String>> findAll() {
        List<String> roles = roleService.find();
        log.info("Roles : {} ", roles);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}