package com.example.rentify.controller;

import com.example.rentify.dto.RentalDTO;
import com.example.rentify.service.RentalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rental")
public class RentalController {

    private final RentalService rentalService;

    @PostMapping //POST http://localhost:8080/api/rental/
    public ResponseEntity<Void> insert(@RequestBody RentalDTO rentalDTO) {
        if (rentalDTO.getId() != null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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

    @DeleteMapping("{id}") //DELETE http://localhost:8080/api/rental/2
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