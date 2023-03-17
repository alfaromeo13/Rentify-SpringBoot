package com.example.rentify.controller;

import com.example.rentify.dto.RoleDTO;
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

    @PostMapping //POST http://localhost:8080/api/role/  {"name": "user"}
    public ResponseEntity<Void> insert(@RequestBody RoleDTO roleDTO) {
        if (roleDTO.getId() != null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        log.info("Storing role : {} ", roleDTO);
        roleService.save(roleDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}") //DELETE http://localhost:8080/api/role/2
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Deleting role with id: {} ", id);
        boolean deleted = roleService.delete(id);
        return new ResponseEntity<>(deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}") //PUT http://localhost:8080/api/role/2  {"name":"user"}
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody RoleDTO roleDTO) {
        log.info("Updating role with id : {} ", id);
        boolean updated = roleService.update(id, roleDTO);
        return new ResponseEntity<>(updated ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}