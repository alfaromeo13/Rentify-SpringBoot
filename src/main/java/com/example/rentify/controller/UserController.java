package com.example.rentify.controller;

import com.example.rentify.dto.RoleDTO;
import com.example.rentify.dto.UserDTO;
import com.example.rentify.dto.UserWithRolesDTO;
import com.example.rentify.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /*{"firstName":"Jovan","lastName": "Vukovic","email":"bula20@gmail.com",
       "username":"bula12345!","password":"`1212344312134"} */
    @PostMapping //POST http://localhost:8080/api/user
    public ResponseEntity<Void> insert(@RequestBody UserDTO userDTO) {
        if (userDTO.getId() != null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        log.info("Storing user : {} ", userDTO);
        userService.save(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //update user

    //delete user

    //get all user info

    //get favourite apartments

    //get rentals-kome si sto izdao

    //{"id":3, "name":"admin"}
    @PutMapping("{id}/add-role") //PUT http://localhost:8080/api/user/10/add-role
    public ResponseEntity<Void> updateUserRole(@PathVariable Integer id, @RequestBody RoleDTO roleDTO) {
        boolean updated = userService.addRole(id, roleDTO);
        log.info("Adding a role to user with id={}", id);
        return new ResponseEntity<>(updated ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("with-roles/{id}") //GET http://localhost:8080/api/user/with-roles/9
    public ResponseEntity<UserWithRolesDTO> getById(@PathVariable Integer id) {
        UserWithRolesDTO user = userService.findWithRoles(id);
        log.info("User : {} ", user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}