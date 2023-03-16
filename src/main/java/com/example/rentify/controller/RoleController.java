package com.example.rentify.controller;

import com.example.rentify.dto.RoleDTO;
import com.example.rentify.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;
    private final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @GetMapping //GET http://localhost:8080/api/role/
    public ResponseEntity<List<String>> findAll() {
        List<String> roles = roleService.find();
        LOGGER.info("Roles : {} ", roles);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping //POST http://localhost:8080/api/role/  {"name": "user"}
    public ResponseEntity<Void> insert(@RequestBody RoleDTO roleDTO) {
        if (roleDTO.getId() != null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        LOGGER.info("Storing role : {} ", roleDTO);
        roleService.save(roleDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}") //DELETE http://localhost:8080/api/role/2
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        LOGGER.info("Deleting role with id: {} ", id);
        boolean deleted = roleService.delete(id);
        return new ResponseEntity<>(deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}") //PUT http://localhost:8080/api/role/2  {"name":"user"}
    public ResponseEntity<Void> update(@PathVariable Integer id,
                                       @RequestBody RoleDTO roleDTO) {
        LOGGER.info("Updating role with id : {} ", id);
        boolean updated = roleService.update(id, roleDTO);
        return new ResponseEntity<>(updated ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}