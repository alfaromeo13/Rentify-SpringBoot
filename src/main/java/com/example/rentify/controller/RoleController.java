package com.example.rentify.controller;

import com.example.rentify.dto.RoleDTO;
import com.example.rentify.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<Void> store(@RequestBody RoleDTO roleDTO) {
        if (roleDTO.getId() != null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        roleService.save(roleDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> store(@PathVariable Integer id,
                                      @RequestBody RoleDTO roleDTO) {
        if (roleDTO.getId() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        boolean updated = roleService.update(id, roleDTO);
        return updated ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}