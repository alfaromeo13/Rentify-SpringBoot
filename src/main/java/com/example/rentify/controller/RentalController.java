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
@RequestMapping("/api/rental")
public class RentalController {

    private final RentalService rentalService;
    private final RentalValidator rentalValidator;
    private final RentalSearchValidator rentalSearchValidator;

    /*
    {
        "id":4,
        "startDate":"2023-04-20 16:31:00",
        "endDate":"2023-04-23 23:29:00"
    }
     */
    //GET -> api da kada izabere mjesec u kalendaru [od do period se salje] vracemo zauzete termine za taj period
    // takodje cemo onemoguciti klik tih datuma na kalendaru
    @SneakyThrows
    @GetMapping("availability")
    public ResponseEntity<List<RentalSearchDTO>> getRentedForSpecifiedMonth(@RequestBody RentalSearchDTO rentalSearchDTO) {
        Errors errors = new BeanPropertyBindingResult(rentalSearchDTO, "rentalSearchDTO");
        ValidationUtils.invokeValidator(rentalSearchValidator, rentalSearchDTO, errors);
        if (errors.hasErrors()) throw new ValidationException("Validation error", errors);
        List<RentalSearchDTO> rentals = rentalService.findRentalsForSpecifPeriod(rentalSearchDTO);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    //GET -> izlistaj sve apartmane koje je korisnik iznajmio do sada(a klikom na preview dugme pozovi apartment
    //api koji nalazi taj stan preko rental id-a) ?????????????????
    @GetMapping("for-user") //http://localhost:8080/api/rental/for-user?page=0&size=5
    public ResponseEntity<List<RentalDTO>> usersRentingHistory(Pageable pageable) {
        List<RentalDTO> rentals = rentalService.userRentedHistory(pageable);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    //GET ->renting history za odredjeni stan (samo vlasnik moze ovo vidjet)
    @GetMapping("apartment-id/{id}") //GET http://localhost:8080/api/rental/apartment-id/5?page=0&size=5
    @PreAuthorize("@apartmentAuth.hasPermission(#id)")
    public ResponseEntity<List<RentalDTO>> apartmentRentalHistory(@PathVariable Integer id, Pageable pageable) {
        List<RentalDTO> rentals = rentalService.findByApartmentId(id, pageable);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    /*{  DODAJ VALIDACIJU DATUMA JOS!!!!!
    "startDate":"2023-04-22 13:30:00",
    "endDate":"2023-04-22 17:30:00",
    "rentalPrice":155,
    "apartmentId":4
    }*/

    @PostMapping//POST->api za dodavanje rezervacija za odredjeni stan [saljemo od do period].Kad se izda stan poslaji
    @SneakyThrows// notifikaciju vlasniku http://localhost:8080/api/rental/ (kada klijent bude rezervisao stan)
    public ResponseEntity<Void> insert(@RequestBody RentalApartmentDTO rentalApartmentDTO) {
        Errors errors = new BeanPropertyBindingResult(rentalApartmentDTO, "rentalApartmentDTO");
        ValidationUtils.invokeValidator(rentalValidator, rentalApartmentDTO, errors);
        if (errors.hasErrors()) throw new ValidationException("Validation error", errors);
        log.info("Adding new rental: {}", rentalApartmentDTO);
        rentalService.save(rentalApartmentDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //DELETE ->api za cancel rentala (preko redisa obavijestiti putem notifikacije klijente o bilo kakvoj promjeni) ???
    @DeleteMapping("cancel/{id}") //PUT http://localhost:8080/api/rental/2
    @PreAuthorize("@rentalAuth.hasPermission(#id)") //samo gazda stana moze da ponisti rental
    public ResponseEntity<Void> cancel(@PathVariable Integer id) {
        log.info("Updating rental with id : {} ", id);
        rentalService.cancel(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}