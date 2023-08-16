package com.example.rentify.repository;

import com.example.rentify.entity.Rental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
    @Query(value = "select rental from Rental rental " +
            "join rental.apartment apartment where apartment.user.username = :username " +
            "and function('YEAR', rental.startDate) = function('YEAR', current_date) ")
    List<Rental> findAllRentalsForUsersApartments(@Param("username") String username);

    @Query(value = "select rental from Rental rental join rental.user user " +
            "where user.username= :name and rental.status.name <> 'cancelled' order by rental.id desc")
    Page<Rental> findRentalsByUsername(@Param("name") String username, Pageable pageable);

    List<Rental> findByStatusNameAndEndDate(String statusName, Date today);

    boolean existsByIdAndApartmentUserUsername(Integer id, String username);

    @Query(value = "select rental from Rental rental " +
            "join rental.status status join rental.apartment apartment " +
            "where apartment.id = :id and status = 'rented' and (" +
            "(rental.startDate <= current_date and rental.endDate > current_date) or" +
            "(rental.startDate >= current_date))")
    List<Rental> getRented(@Param("id") Integer id);

    @Query(value = "select rental from Rental rental " +
            "join rental.status status join rental.apartment apartment " +
            "where apartment.id = :id and status = 'rented' and " +
            "rental.startDate < :endDate and rental.endDate > :startDate ")
    List<Rental> findForPeriod(@Param("id") Integer id, @Param("startDate") Date start, @Param("endDate") Date end);

    @Query("select rental from Rental rental join rental.user user join rental.apartment apartment " +
            " where apartment.user.username = :owner and (:to is null or rental.startDate <= :to)"
            + " and (:from is null or rental.endDate >= :from)"
            + " and (:username is null or user.username like concat(:username,'%'))"
            + " and (:propertyTitle is null or apartment.title like concat(:propertyTitle,'%')) order by rental.id desc")
    Page<Rental> findFilteredRentals(@Param("to") Date to, @Param("from") Date from,
                                     @Param("username") String username,
                                     @Param("propertyTitle") String propertyTitle,
                                     @Param("owner") String owner, Pageable pageable);
}