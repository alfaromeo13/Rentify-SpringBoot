package com.example.rentify.controller;

import com.example.rentify.dto.RoleDTO;
import com.example.rentify.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Void> store(@RequestBody RoleDTO roleDTO) {
        if (roleDTO.getId() != null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        roleService.save(roleDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id,
                                       @RequestBody RoleDTO roleDTO) {
        boolean updated = roleService.update(id, roleDTO);
        return new ResponseEntity<>(updated ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
/*
    PUT http://localhost:8080/api/role/2
    {
        "name":"user"
    }
 */
}