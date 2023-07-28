package com.example.rentify.controller;

import com.example.rentify.dto.UserDTO;
import com.example.rentify.projections.AdminApartmentProjection;
import com.example.rentify.service.ApartmentService;
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
    private final ApartmentService apartmentService;

    @GetMapping("magic-numbers") //numbers of active properties
    public ResponseEntity<AdminApartmentProjection> getNumbers(){
        return new ResponseEntity<>(apartmentService.getProjection(),HttpStatus.OK);
    }

    @GetMapping("num-of-users")//number of users (both active and inactive)
    public ResponseEntity<Integer> getNumOfUsers(){
        return new ResponseEntity<>(userService.numOfUsers(),HttpStatus.OK);
    }

    @GetMapping("all-users") //list of all users
    public ResponseEntity<List<UserDTO>> findAll(Pageable pageable) {
        return new ResponseEntity<>(userService.find(pageable), HttpStatus.OK);
    }

    @GetMapping("users-by-username/{name}") //list of users with matching username
    public ResponseEntity<List<UserDTO>> findByUsername(@PathVariable String name, Pageable pageable) {
        return new ResponseEntity<>(userService.findAllByUsername(name,pageable), HttpStatus.OK);
    }

    @PutMapping("user/{id}") //activate user
    public ResponseEntity<Void> activateUser(@PathVariable Integer id) {
        //posalji mail useru da si mu aktivirao account
        userService.activateById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("user/{id}") //delete user
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        //posalji mail useru da si ga banovao banovan
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("apartment/{id}") //activate apartment
    public ResponseEntity<Void> activateApartment(@PathVariable Integer id) {
        apartmentService.activateById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("apartment/{id}") //deactivate apartment
    public ResponseEntity<Void> deleteApartment(@PathVariable Integer id) {
        apartmentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("approve/{id}") //approve apartment
    public ResponseEntity<Void> approveApartment(@PathVariable Integer id) {
        apartmentService.approveById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("approve/{id}") //delete not approved apartment
    public ResponseEntity<Void> removeApartment(@PathVariable Integer id) {
        apartmentService.notApprove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}