package com.example.rentify.controller;

import com.example.rentify.dto.RentalDTO;
import com.example.rentify.exception.ValidationException;
import com.example.rentify.service.RentalService;
import com.example.rentify.validator.RentalValidator;
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
@RequestMapping("/api/rental")
public class RentalController {

    private final RentalService rentalService;
    private final RentalValidator rentalValidator;

    //->api (read)  da ti vrati datume kada je stan zauzet za proslijedjeni datum od do (tj za odredjeni period koji nam salje
    //korisnik treba da vratimo je li stan zauzet

    //->api za post tj da se doda rezervacija za period od do(validacija da viidmo je li taj period dostupan i sl)
    //ovo ce dodati rezervaciju na waiting u bazi(.Kada izabere i potvrdi salje se rentalDTO klasa bekendu u POST.)
    //Kada klijent posalje za koji period hoce stan notifikacija se salje vlasniku (poseban kanal preko redisa napravi za notifikacije)

    //->api za updejt rentala()
    // updejt statusa da mzoe da se uradi. Da li staviti insertable i updateable na ona polja i u validatoru da li ga validirati]
    //ako stavi na rented a bilo je vise waiting zahtjeva,i prihvatimo samo jednoga ostali se
    // rejectuju tj izbrisacemo ih iz baze (preko redisa obavijestiti putem notifikacije klijente o bilo kakvoj promjeni)
    //poseban api za  cancel rentala ili sa ovim updejtom?

    //->api za (read) history rentala odredjenog stan (rentala sort desc)

    //->api za (read) history rentala korisnika dje je iznajmnljivao prije sto i sl( i sad trenutno aktivne i sl)

    //->api za (read) prikaz svih stanova vlasniku koji imaju waiting na sebi za koje je neko poslao da hoce
    //da ga iznajmi

    @PostMapping
    @SneakyThrows //POST http://localhost:8080/api/rental/ kada vlasnik stana bude izdao stan nekome
    public ResponseEntity<Void> insert(@RequestBody RentalDTO rentalDTO) {
        Errors errors = new BeanPropertyBindingResult(rentalDTO, "rentalDTO");
        ValidationUtils.invokeValidator(rentalValidator, rentalDTO, errors);
        if (errors.hasErrors()) throw new ValidationException("Validation error", errors);
        log.info("Adding new rental: {}", rentalDTO);
        rentalService.save(rentalDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}") //PUT http://localhost:8080/api/rental/2
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody RentalDTO rentalDTO) {
        log.info("Updating rental with id : {} ", id);
        boolean updated = rentalService.update(id, rentalDTO);
        return new ResponseEntity<>(updated ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}") //DELETE http://localhost:8080/api/rental/2 softDelete
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Deleting rental with id: {} ", id);
        boolean deleted = rentalService.delete(id);
        return new ResponseEntity<>(deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    //Kad korisnik sa nalogom zeli da imam spisak svih svojih izadavanja za
    //odrejdnei stan sa tim i tim id-em.Vrace sa order by id desc rentale tj od najnovijih dodatih
    @GetMapping("by-apartment-id/{id}") //GET http://localhost:8080/api/rental/by-apartment-id/5?page=0&size=5
    public ResponseEntity<List<RentalDTO>> findForApartment(@PathVariable Integer id, Pageable pageable) {
        List<RentalDTO> rentals = rentalService.findByApartmentId(id, pageable);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }
}