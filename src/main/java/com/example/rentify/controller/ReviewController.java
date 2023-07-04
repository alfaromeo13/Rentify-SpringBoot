package com.example.rentify.controller;

import com.example.rentify.dto.ReviewApartmentDTO;
import com.example.rentify.dto.ReviewDTO;
import com.example.rentify.exception.ValidationException;
import com.example.rentify.service.ReviewService;
import com.example.rentify.validator.ReviewValidator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewValidator reviewValidator;

    //da uzmemo komentare za 1 stan sortirane od najnovijeg do najstarijeg(samo sortiamo po id-evi u opadajucem redolslj)
    @GetMapping("apartment-id/{id}") //GET http://localhost:8080/api/review/apartment-id/5?page=0&size=5
    public ResponseEntity<List<ReviewDTO>> findForApartment(@PathVariable Integer id, Pageable pageable) {
        List<ReviewDTO> reviews = reviewService.findByApartmentId(id, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        //ovakvo predavanje pageabla radimo da nam neko ne posalje ogroman page i dovuce npr 10 na 6 rekorda iz baze i sl
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping
    @SneakyThrows //POST http://localhost:8080/api/review/ kada budemo dodavali komentar na stan
    public ResponseEntity<Void> create(@RequestBody ReviewApartmentDTO reviewApartmentDTO) {
        Errors errors = new BeanPropertyBindingResult(reviewApartmentDTO, "reviewApartmentDTO");//ovaj drugi parametar je buklvalno naziv objekta kojeg predajemo
        ValidationUtils.invokeValidator(reviewValidator, reviewApartmentDTO, errors);//pozivamo odredjeni validator
        if (errors.hasErrors()) throw new ValidationException("Validation error", errors);//hvata RestExcHendler klasa
        log.info("Adding review : {} ", reviewApartmentDTO);
        reviewService.save(reviewApartmentDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @SneakyThrows //ako budemo zeljeli da editujemo komentar
    @PutMapping("{id}") //PUT http://localhost:8080/api/review/55
    @PreAuthorize("@reviewAuth.hasPermission(#id)")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody ReviewApartmentDTO reviewApartmentDTO) {
        Errors errors = new BeanPropertyBindingResult(reviewApartmentDTO, "reviewApartmentDTO");
        ValidationUtils.invokeValidator(reviewValidator, reviewApartmentDTO, errors);
        if (errors.hasErrors()) throw new ValidationException("Validation error", errors);
        reviewService.update(id, reviewApartmentDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}") //DELETE http://localhost:8080/api/review/55 za brisanje komentara
    @PreAuthorize("@reviewAuth.hasPermission(#id)")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        reviewService.delete(id);
        log.info("Deleted review id : {} ", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /* for POST or PUT
        {
        "grade":5,
        "comment":"komentar",
        "apartmentId":4
        }
 */
}