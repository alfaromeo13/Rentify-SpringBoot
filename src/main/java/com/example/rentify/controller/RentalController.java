package com.example.rentify.controller;

import com.example.rentify.dto.RentalApartmentDTO;
import com.example.rentify.dto.RentalDTO;
import com.example.rentify.dto.RentalSearchDTO;
import com.example.rentify.exception.ValidationException;
import com.example.rentify.service.RentalService;
import com.example.rentify.validator.RentalSearchValidator;
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
    private final RentalSearchValidator rentalSearchValidator;

    /*{
        "id":4,
        "startDate":"2023-04-20",
        "endDate":"2023-04-23"
    } */
    //GET -> api da kada udjemo na stan i izabere mjesec u kalendaru [od do period se salje] vracemo zauzete termine
    // za taj period takodje cemo diseblovati klik tih datuma na kalendaru
    @SneakyThrows
    @PostMapping("availability")
    public ResponseEntity<List<RentalSearchDTO>> getRentedForSpecifiedMonth(@RequestBody RentalSearchDTO rentalSearchDTO) {
        Errors errors = new BeanPropertyBindingResult(rentalSearchDTO, "rentalSearchDTO");
        ValidationUtils.invokeValidator(rentalSearchValidator, rentalSearchDTO, errors);
        if (errors.hasErrors()) throw new ValidationException("Validation error", errors);
        List<RentalSearchDTO> rentals = rentalService.findRentalsForSpecifPeriod(rentalSearchDTO);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
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

    /*{
    "startDate": "2023-05-10",
    "endDate": "2023-05-30",
    "apartmentId": 1
    }*/
    @SneakyThrows//front salje da za izabrani stan i period dobije ukupnu cijenu iznajmljivanja
    @PostMapping("calculate-price") //http://localhost:8080/api/rental/calculate-price
    public ResponseEntity<Double> calculatePrice(@RequestBody RentalApartmentDTO rentalApartmentDTO) {
        Errors errors = new BeanPropertyBindingResult(rentalApartmentDTO, "rentalApartmentDTO");
        ValidationUtils.invokeValidator(rentalValidator, rentalApartmentDTO, errors);
        if (errors.hasErrors()) throw new ValidationException("Validation error", errors);
        return new ResponseEntity<>(rentalService.calculatePrice(rentalApartmentDTO), HttpStatus.OK);
    }

    /* kada budemo dodavali renting za stan na dan biracemo dane sa kalendara,slicno za stan na mejsecnom nivou
       slacemo samo mjesece a za stan na godisnjem nivou slacemo samo godine
    {
    "startDate":"2023-04-22",
    "endDate":"2024-05-22",
    "apartmentId":3
    }*/
    @PostMapping//POST->api za dodavanje rezervacija za odredjeni stan [saljemo od do period].Kad se izda stan posalji
    @SneakyThrows// notifikaciju vlasniku http://localhost:8080/api/rental/ (kada klijent bude rezervisao stan)
    public ResponseEntity<Void> insert(@RequestBody RentalApartmentDTO rentalApartmentDTO) {
        Errors errors = new BeanPropertyBindingResult(rentalApartmentDTO, "rentalApartmentDTO");
        ValidationUtils.invokeValidator(rentalValidator, rentalApartmentDTO, errors);
        if (errors.hasErrors()) throw new ValidationException("Validation error", errors);
        rentalService.save(rentalApartmentDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //DELETE ->api za cancel rentala (preko redisa obavijestiti putem notifikacije klijente o bilo kakvoj promjeni)
    @DeleteMapping("cancel/{id}") //DELETE http://localhost:8080/api/rental/cancel/2
    @PreAuthorize("@rentalAuth.hasPermission(#id)") //samo gazda stana stana moze da ponisti rental za svoj stan
    public ResponseEntity<Void> cancel(@PathVariable Integer id) {
        rentalService.cancel(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}