package com.example.rentify.service;

import com.example.rentify.dto.NotificationDTO;
import com.example.rentify.dto.RentalApartmentDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalMapper rentalMapper;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;
    private final RentalSearchMapper rentalSearchMapper;
    private final NotificationService notificationService;
    private final ApartmentRepository apartmentRepository;

    public List<RentalSearchDTO> findRentalsForSpecifPeriod(Integer id) {
        List<Rental> rentals = rentalRepository.getRented(id);
        return rentalSearchMapper.toDTOList(rentals);
    }

    public List<RentalApartmentDTO> userRentingHistory(Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<Rental> rentals = rentalRepository.findRentalsByUsername(username, pageable);
        return rentals.getContent().stream().map(rentalMapper::toRentalDTO).collect(Collectors.toList());
    }

    //we schedule a task to run every day at midnight (00:00:00) at the beginning of each day.
    @Scheduled(cron = "0 0 * * * *") // cron expressions
    @CacheEvict(value = "rentals", allEntries = true)
    public void checkEnded() {
        log.info("check for ended rental (cron job triggered...)");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1); // Subtract 1 day
        List<Rental> rentals = rentalRepository.findByStatusNameAndEndDate("rented", calendar.getTime());
        rentals.forEach(rental -> rental.setStatus(new Status("ended")));
        rentalRepository.saveAll(rentals);
    }

    public String transformDateToString(Date inputDate) {
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return outputDateFormat.format(inputDate);
    }

    @CacheEvict(value = "rentals", allEntries = true)
    public void cancel(Integer id) {
        Rental rental = rentalRepository.getById(id);
        rental.setStatus(new Status("cancelled"));
        rentalRepository.save(rental);
        NotificationDTO notification=new NotificationDTO();
        notification.setMessage("Sorry... your booking from: "+transformDateToString(rental.getStartDate())
                +" to: "+transformDateToString(rental.getEndDate())
                +" for "+rental.getApartment().getPropertyType().getName().toLowerCase()+" in "
                +rental.getApartment().getAddress().getStreet()+" "
                +rental.getApartment().getAddress().getNeighborhood().getName()+" ,"
                +rental.getApartment().getAddress().getNeighborhood().getCity().getName()+" has been cancelled");
        notification.setReceiverUsername(rental.getUser().getUsername());
        notificationService.save(notification);
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
        NotificationDTO notification=new NotificationDTO();
        notification.setMessage(" booked your "+
                rental.getApartment().getPropertyType().getName().toLowerCase()+" in"
                +" "+rental.getApartment().getAddress().getStreet()+" "
                +rental.getApartment().getAddress().getNeighborhood().getName()+" , "
                +rental.getApartment().getAddress().getNeighborhood().getCity().getName()+" "
        +"from: "+transformDateToString(rental.getStartDate())+" to: "+transformDateToString(rental.getEndDate()));
        notification.setReceiverUsername(rental.getApartment().getUser().getUsername());
        notificationService.save(notification);
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
        price *= period.equals("day") ? days(startDate, endDate) + 1: months(startDate, endDate);
        return Math.round(price * 100.0) / 100.0;
    }

    public double[] calculateMonthlyEarnings() {
        double[] monthlyEarnings = new double[12];
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Rental> allRentals = rentalRepository.findAllRentalsForUsersApartments(username);
        for (Rental rental : allRentals) {
            if(rental.getStatus().getName().equalsIgnoreCase("cancelled"))
                continue;
            Date startDate = rental.getStartDate();
            Date endDate = rental.getEndDate();
            Double rentalPrice = rental.getRentalPrice();

            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startDate);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endDate);

            while (startCalendar.before(endCalendar)) {
                int month = startCalendar.get(Calendar.MONTH);
                monthlyEarnings[month] += rentalPrice;
                startCalendar.add(Calendar.MONTH, 1);
            }
        }
        return monthlyEarnings;
    }

    public List<RentalApartmentDTO> filter(Date to, Date from, String username, String propertyTitle,Pageable pageable) {
        String owner = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<Rental> rentals = rentalRepository.findFilteredRentals(to,from,username,propertyTitle,owner,pageable);
        return rentals.getContent().stream().map(rentalMapper::toRentalDTO).collect(Collectors.toList());
    }
}