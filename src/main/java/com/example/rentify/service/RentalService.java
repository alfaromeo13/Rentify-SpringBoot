package com.example.rentify.service;

import com.example.rentify.dto.RentalApartmentDTO;
import com.example.rentify.dto.RentalDTO;
import com.example.rentify.dto.RentalSearchDTO;
import com.example.rentify.entity.*;
import com.example.rentify.mapper.RentalMapper;
import com.example.rentify.mapper.RentalSearchMapper;
import com.example.rentify.repository.ApartmentRepository;
import com.example.rentify.repository.RentalRepository;
import com.example.rentify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalMapper rentalMapper;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;
    private final RentalSearchMapper rentalSearchMapper;
    private final ApartmentRepository apartmentRepository;

    public List<RentalSearchDTO> findRentalsForSpecifPeriod(Integer id) {
        List<Rental> rentals = rentalRepository.getRented(id);
        return rentalSearchMapper.toDTOList(rentals);
    }

    @Cacheable(value = "rental-history", key = "{#pageable.toString()}")
    public List<RentalDTO> userRentingHistory(Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<Rental> rentals = rentalRepository.findRentalsByUsername(username, pageable);
        return rentalMapper.toDTOList(rentals.getContent());
    }

    @Cacheable(value = "rentals", key = "{#id,#pageable.toString()}")
    public List<RentalDTO> findByApartmentId(Integer id, Pageable pageable) {
        Page<Rental> rentalPage = rentalRepository.findRentalsByApartmentId(id, pageable);
        return rentalPage.hasContent() ? rentalMapper.toDTOList(rentalPage.getContent()) : Collections.emptyList();
    }

    //we schedule a task to run every hour
    @Scheduled(cron = "0 0 * * * *") // cron expressions
    public void checkEnded() {
        log.info("cron job triggered...");
        List<Rental> rentals = rentalRepository.findByStatusNameAndEndDate("rented", new Date());
        rentals.forEach(rental -> rental.setStatus(new Status("ended")));
        rentalRepository.saveAll(rentals);
    }

    @CacheEvict(value = "rentals", allEntries = true)
    public void cancel(Integer id) {
        Rental rental = rentalRepository.getById(id);
        rental.setStatus(new Status("cancelled"));
        rentalRepository.save(rental);
    }

    @CacheEvict(value = "rentals", allEntries = true)
    public void save(RentalApartmentDTO rentalApartmentDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        Rental rental = rentalMapper.toEntity(rentalApartmentDTO);
        rental.setRentalPrice(calculatePrice(rentalApartmentDTO));
        rental.setApartment(apartmentRepository.getById(rentalApartmentDTO.getApartmentId()));
        rental.setUser(user);
        rentalRepository.save(rental);
    }

    public List<Rental> findForPeriod(Integer id, Date start, Date end) {
        return rentalRepository.findForPeriod(id, start, end);
    }

    //number of days between 2 dates
    private long days(Date firstDate, Date secondDate) {
        return ChronoUnit.DAYS.between(firstDate.toInstant(), secondDate.toInstant());
    }

    //number of months between 2 dates
    private long months(Date firstDate, Date secondDate) {
        YearMonth m1 = YearMonth.from(firstDate.toInstant().atZone(ZoneOffset.UTC));
        YearMonth m2 = YearMonth.from(secondDate.toInstant().atZone(ZoneOffset.UTC));
        return m1.until(m2, ChronoUnit.MONTHS);
    }

    public Double calculatePrice(RentalApartmentDTO rentalApartmentDTO) {
        Rental rental = rentalMapper.toEntity(rentalApartmentDTO);
        Apartment apartment = apartmentRepository.getById(rentalApartmentDTO.getApartmentId());
        String period = apartment.getPeriod().getName();
        Double price = apartment.getPrice();
        Date startDate = rental.getStartDate();
        Date endDate = rental.getEndDate();
        price *= period.equals("day") ? days(startDate, endDate) : months(startDate, endDate);
        return Math.round(price * 100.0) / 100.0;
    }
}