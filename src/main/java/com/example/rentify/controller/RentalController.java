package com.example.rentify.controller;

import com.example.rentify.dto.RentalApartmentDTO;
import com.example.rentify.dto.RentalDTO;
import com.example.rentify.dto.RentalSearchDTO;
import com.example.rentify.exception.ValidationException;
import com.example.rentify.service.RentalService;
import com.example.rentify.validator.RentalValidator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rental")
public class RentalController {

    private final RentalService rentalService;
    private final RentalValidator rentalValidator;

    @SneakyThrows
    @GetMapping("availability/{id}")
    public ResponseEntity<List<RentalSearchDTO>> getRentedForSpecifiedMonth(@PathVariable Integer id) {
        List<RentalSearchDTO> rentals = rentalService.findRentalsForSpecifPeriod(id);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/monthly-incomes")
    public ResponseEntity<double[]> getMonthlyEarnings() {
        return new ResponseEntity<>(rentalService.calculateMonthlyEarnings(), HttpStatus.OK);
    }

    //GET -> izlistaj sve apartmane koje je korisnik iznajmio do sada(a klikom na preview dugme pozovi apartment
    //api koji nalazi taj stan preko rental id-a)
    @GetMapping("for-user") //http://localhost:8080/api/rental/for-user?page=0&size=5
    public ResponseEntity<List<RentalDTO>> usersRentalHistory(Pageable pageable) {
        List<RentalDTO> rentals = rentalService.userRentingHistory(pageable);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    //GET ->renting history za odredjeni stan (samo vlasnik moze ovo da vidi)
    @GetMapping("apartment-id/{id}") //GET http://localhost:8080/api/rental/apartment-id/5?page=0&size=5
    @PreAuthorize("@apartmentAuth.hasPermission(#id)")
    public ResponseEntity<List<RentalDTO>> apartmentRentalHistory(@PathVariable Integer id, Pageable pageable) {
        List<RentalDTO> rentals = rentalService.findByApartmentId(id, pageable);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping("calculate-price") //http://localhost:8080/api/rental/calculate-price
    public ResponseEntity<Double> calculatePrice(@RequestBody RentalApartmentDTO rentalApartmentDTO) {
        Errors errors = new BeanPropertyBindingResult(rentalApartmentDTO, "rentalApartmentDTO");
        ValidationUtils.invokeValidator(rentalValidator, rentalApartmentDTO, errors);
        if (errors.hasErrors()) throw new ValidationException("Validation error", errors);
        return new ResponseEntity<>(rentalService.calculatePrice(rentalApartmentDTO), HttpStatus.OK);
    }

    @PostMapping
    @SneakyThrows //http://localhost:8080/api/rental/
    public ResponseEntity<Void> insert(@RequestBody RentalApartmentDTO rentalApartmentDTO) {
        Errors errors = new BeanPropertyBindingResult(rentalApartmentDTO, "rentalApartmentDTO");
        ValidationUtils.invokeValidator(rentalValidator, rentalApartmentDTO, errors);
        if (errors.hasErrors()) throw new ValidationException("Validation error", errors);
        rentalService.save(rentalApartmentDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //DELETE ->api za cancel rentala
    @DeleteMapping("cancel/{id}") //DELETE http://localhost:8080/api/rental/cancel/2
    @PreAuthorize("@rentalAuth.hasPermission(#id)") //samo gazda stana stana moze da ponisti rental za svoj stan
    public ResponseEntity<Void> cancel(@PathVariable Integer id) {
        rentalService.cancel(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}