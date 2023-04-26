package com.example.rentify.controller;

import com.example.rentify.dto.*;
import com.example.rentify.exception.ValidationException;
import com.example.rentify.service.UserService;
import com.example.rentify.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final UserValidator userValidator;

    //dodaj u filter u JWT da ti ne dozvoljava poziv apija ako je user isActive=false !!!!!!!!!

    @GetMapping //GET http://localhost:8080/api/user
    public ResponseEntity<UserDTO> userInfo() {
        return new ResponseEntity<>(userService.find(), HttpStatus.OK);
    }

    @PutMapping//update user(samo njegove informacije,a za update stana ima ApartmentController)
    @SneakyThrows//PUT http://localhost:8080/api/user/ !!!! ovo popravi
    public ResponseEntity<Void> update(@RequestBody UserDTO userDTO) {
        Errors errors = new BeanPropertyBindingResult(userDTO, "userDTO");
        ValidationUtils.invokeValidator(userValidator, userDTO, errors);
        if (errors.hasErrors()) throw new ValidationException("Validation error", errors);
        userService.update(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //samo admin moze delete za sada (dodaj da ne moze sebe da izbrise)!!!!!!!!!!!!
    @DeleteMapping("{id}") //DELETE http://localhost:8080/api/user/9
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean deleted = userService.delete(id);
        return new ResponseEntity<>(deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    //POST http://localhost:8080/api/user/favourite-apartment/3
    @PostMapping("favourite-apartment/{id}")
    public ResponseEntity<Void> addFavourite(@PathVariable Integer id) {
        boolean updated = userService.addFavourites(id);
        return new ResponseEntity<>(updated ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    //DELETE http://localhost:8080/api/user/favourite-apartment/3
    @DeleteMapping("favourite-apartment/{id}")
    public ResponseEntity<Void> deleteFavourite(@PathVariable Integer id) {
        userService.deleteFavourites(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //nadjemo id-eve omiljenih i onda pozovemo api search apartmana sa tim id-evima
    //GET http://localhost:8080/api/user/favourite-apartments-for-user?page=0&size=5
    @GetMapping("favourite-apartments-for-user")
    public ResponseEntity<List<Integer>> getFavouriteIds(Pageable pageable) {
        List<Integer> apartmentIds = userService.getFavourites(pageable);
        return new ResponseEntity<>(apartmentIds, HttpStatus.OK);
    }

    //vrati sve korisnike (ovo moze samo admin da pozove)
    @GetMapping("all-users") //GET http://localhost:8080/api/user/all-users?page=0&size=5
    public ResponseEntity<List<UserDTO>> findAll(Pageable pageable) {
        List<UserDTO> users = userService.findAll(pageable);
        log.info("Users with page : {} ", users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}