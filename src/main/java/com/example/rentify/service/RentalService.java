package com.example.rentify.service;

import com.example.rentify.dto.RentalDTO;
import com.example.rentify.entity.Rental;
import com.example.rentify.entity.Review;
import com.example.rentify.mapper.RentalMapper;
import com.example.rentify.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalMapper rentalMapper;
    private final RentalRepository rentalRepository;

    public void save(RentalDTO rentalDTO) {
        rentalRepository.save(rentalMapper.toEntity(rentalDTO));
    }

    public Boolean update(Integer id, RentalDTO rentalDTO) {
        boolean rentalExists = rentalRepository.existsById(id);
        if (rentalExists) {
            rentalDTO.setId(id);
            save(rentalDTO);
            return true;
        } else return false;
    }

    public Boolean delete(Integer id) {
        Optional<Rental> rentalOptional = rentalRepository.findById(id);
        if (rentalOptional.isPresent()) {
            Rental rental = rentalOptional.get();
            rental.setAvailable(false);//?????
            rentalRepository.save(rental);
            return true;
        } else return false;
    }

    @Cacheable(value = "rentals", key = "#id + ':' + #pageable.toString()")
    public List<RentalDTO> findByApartmentId(Integer id, Pageable pageable) {
        Page<Rental> rentalPage = rentalRepository.findReviewsByApartmentId(id, pageable);
        return rentalPage.hasContent() ?
                rentalMapper.toDTOList(rentalPage.getContent()) :
                Collections.emptyList();
    }
}