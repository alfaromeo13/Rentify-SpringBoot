package com.example.rentify.service;

import com.example.rentify.dto.RentalApartmentDTO;
import com.example.rentify.dto.RentalDTO;
import com.example.rentify.dto.RentalSearchDTO;
import com.example.rentify.entity.Rental;
import com.example.rentify.entity.User;
import com.example.rentify.mapper.RentalMapper;
import com.example.rentify.mapper.RentalSearchMapper;
import com.example.rentify.mapper.UserMapper;
import com.example.rentify.repository.ApartmentRepository;
import com.example.rentify.repository.RentalRepository;
import com.example.rentify.repository.StatusRepository;
import com.example.rentify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@CacheEvict(value = "rentals", allEntries = true)
public class RentalService {

    private final UserMapper userMapper;
    private final RentalMapper rentalMapper;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final RentalRepository rentalRepository;
    private final RentalSearchMapper rentalSearchMapper;
    private final ApartmentRepository apartmentRepository;

    @Cacheable(value = "rentals", key = "{#search.getId(),#search.getStartDate(),#search.getEndDate()}")
    public List<RentalSearchDTO> findRentalsForSpecifPeriod(RentalSearchDTO search) {
        List<Rental> rentals = rentalRepository
                .findForSpecifPeriod(search.getId(), search.getStartDate(), search.getEndDate());
        return rentalSearchMapper.toDTOList(rentals);
    }

    @Cacheable(value = "rental-history", key = "{#pageable.toString()}")
    public List<RentalDTO> userRentedHistory(Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<Rental> rentals = rentalRepository.findRentalsByUsername(username, pageable);
        return rentalMapper.toDTOList(rentals.getContent());
    }

    @Cacheable(value = "rentals", key = "{#id,#pageable.toString()}")
    public List<RentalDTO> findByApartmentId(Integer id, Pageable pageable) {
        Page<Rental> rentalPage = rentalRepository.findRentalsByApartmentId(id, pageable);
        return rentalPage.hasContent() ? rentalMapper.toDTOList(rentalPage.getContent()) : Collections.emptyList();
    }

    public void save(RentalApartmentDTO rentalApartmentDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        rentalApartmentDTO.setUser(userMapper.toDTO(user));
        Rental rental = rentalMapper.toEntity(rentalApartmentDTO);
        rental.setApartment(apartmentRepository.getById(rentalApartmentDTO.getApartmentId()));
        rentalRepository.save(rental);
    }

    public void cancel(Integer id) {
        Rental rental = rentalRepository.getById(id);
        rental.setStatus(statusRepository.findByName("cancelled"));
        rentalRepository.save(rental);
    }
}