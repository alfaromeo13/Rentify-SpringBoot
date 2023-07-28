package com.example.rentify.controller;

import com.example.rentify.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @DeleteMapping //DELETE http://localhost:8080/api/user?password=...
    public ResponseEntity<Void> delete(@RequestParam String password) {
        boolean deleted = userService.delete(password);
        return new ResponseEntity<>(deleted?HttpStatus.OK:HttpStatus.NOT_FOUND);
    }

    //POST http://localhost:8080/api/user/favourite-apartment/3
    @PostMapping("favourite-apartment/{id}")
    @PreAuthorize("@apartmentAuth.existsById(#id)")
    public ResponseEntity<Void> addFavourite(@PathVariable Integer id) {
        userService.addFavourites(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //DELETE http://localhost:8080/api/user/favourite-apartment/3
    @DeleteMapping("favourite-apartment/{id}")
    @PreAuthorize("@apartmentAuth.existsById(#id)")
    public ResponseEntity<Void> removeFavourite(@PathVariable Integer id) {
        userService.deleteFavourites(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //GET http://localhost:8080/api/user/favourite-apartment?page=0&size=5
    @GetMapping("favourite-apartment")
    public ResponseEntity<List<Integer>> getFavouriteIds(Pageable pageable) {
        List<Integer> apartmentIds = userService.getFavourites(pageable);
        return new ResponseEntity<>(apartmentIds, HttpStatus.OK);
    }
}