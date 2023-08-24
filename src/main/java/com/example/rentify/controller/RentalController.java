package com.example.rentify.controller;

import com.example.rentify.dto.RentalApartmentDTO;
import com.example.rentify.dto.RentalSearchDTO;
import com.example.rentify.exception.ValidationException;
import com.example.rentify.service.RentalService;
import com.example.rentify.validator.RentalValidator;
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

import java.util.Date;
import java.util.List;

@Slf4j
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

    @GetMapping("filter") //...filter?username=...&propertyTitle=...
    public ResponseEntity<List<RentalApartmentDTO>> getFiltered(
            Pageable pageable,
            @RequestParam(required = false) Date to,
            @RequestParam(required = false) Date from,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String propertyTitle){
        log.info("FROM:{}  TO:{} GUEST USERNAME:{} PROPERTY TITLE:{}",from,to,username,propertyTitle);
        return new ResponseEntity<>(rentalService.filter(to,from,username,propertyTitle,pageable), HttpStatus.OK);
    }

    @GetMapping("monthly-incomes")
    public ResponseEntity<double[]> getMonthlyEarnings() {
        return new ResponseEntity<>(rentalService.calculateMonthlyEarnings(), HttpStatus.OK);
    }

    @GetMapping("for-user")
    public ResponseEntity<List<RentalApartmentDTO>> usersRentalHistory(Pageable pageable) {
        List<RentalApartmentDTO> rentals = rentalService.userRentingHistory(pageable);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }
    
    @SneakyThrows
    @PostMapping("calculate-price")
    public ResponseEntity<Double> calculatePrice(@RequestBody RentalApartmentDTO rentalApartmentDTO) {
        Errors errors = new BeanPropertyBindingResult(rentalApartmentDTO, "rentalApartmentDTO");
        ValidationUtils.invokeValidator(rentalValidator, rentalApartmentDTO, errors);
        if (errors.hasErrors()) throw new ValidationException("Validation error", errors);
        return new ResponseEntity<>(rentalService.calculatePrice(rentalApartmentDTO), HttpStatus.OK);
    }

    @PostMapping
    @SneakyThrows
    public ResponseEntity<Void> insert(@RequestBody RentalApartmentDTO rentalApartmentDTO) {
        Errors errors = new BeanPropertyBindingResult(rentalApartmentDTO, "rentalApartmentDTO");
        ValidationUtils.invokeValidator(rentalValidator, rentalApartmentDTO, errors);
        if (errors.hasErrors()) throw new ValidationException("Validation error", errors);
        rentalService.save(rentalApartmentDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("cancel/{id}")
    @PreAuthorize("@rentalAuth.hasPermission(#id)") //landlord api
    public ResponseEntity<Void> cancel(@PathVariable Integer id) {
        rentalService.cancel(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}