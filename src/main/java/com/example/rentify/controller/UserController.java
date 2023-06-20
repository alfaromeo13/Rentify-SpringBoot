package com.example.rentify.controller;

import com.example.rentify.dto.*;
import com.example.rentify.exception.ValidationException;
import com.example.rentify.service.UserService;
import com.example.rentify.validator.UserUpdateValidator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserUpdateValidator userUpdateValidator;

    @GetMapping //GET http://localhost:8080/api/user
    public ResponseEntity<UserDTO> userInfo() {
        return new ResponseEntity<>(userService.find(), HttpStatus.OK);
    }

    /*{
    "firstName": "John",
    "lastName": "Doe",
    "email":"johndoe@gmail.com"
    }*/
    @PutMapping//update user(samo njegove informacije,a za update stana ima ApartmentController)
    @SneakyThrows//PUT http://localhost:8080/api/user/
    public ResponseEntity<Void> update(@RequestBody UserDTO userDTO) {
        Errors errors = new BeanPropertyBindingResult(userDTO, "userDTO");
        ValidationUtils.invokeValidator(userUpdateValidator, userDTO, errors);
        if (errors.hasErrors()) throw new ValidationException("Validation error", errors);
        userService.update(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //korisnik moze ugasiti svoj nalog
    @DeleteMapping //DELETE http://localhost:8080/api/user/
    public ResponseEntity<Void> delete() {
        userService.delete();
        return new ResponseEntity<>(HttpStatus.OK);
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

    //nadjemo id-eve omiljenih i onda pozovemo api search apartmana sa tim id-evima
    //GET http://localhost:8080/api/user/favourite-apartment?page=0&size=5
    @GetMapping("favourite-apartment")
    public ResponseEntity<List<Integer>> getFavouriteIds(Pageable pageable) {
        List<Integer> apartmentIds = userService.getFavourites(pageable);
        return new ResponseEntity<>(apartmentIds, HttpStatus.OK);
    }
}