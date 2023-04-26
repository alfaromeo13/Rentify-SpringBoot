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
            "join rental.user user join rental.status status " +
            "join rental.apartment apartment where apartment.id= :id order by rental.id desc")
    Page<Rental> findRentalsByApartmentId(@Param("id") Integer id, Pageable pageable);

    @Query(value = "select rental,apartment.id from Rental rental " +
            "join rental.user user join rental.status status " +
            "join rental.apartment apartment where user.username= :name order by rental.id desc")
    Page<Rental> findRentalsByUsername(@Param("name") String username, Pageable pageable);

    @Query(value = "select rental from Rental rental " +
            "join rental.status status join rental.apartment apartment " +
            "where apartment.id = :id and status = 'rented' and (" +
            "(rental.startDate <= :endDate and rental.endDate >= :startDate) or" +
            "(rental.startDate >= :startDate and rental.endDate <= :endDate) or" +
            "(rental.startDate <= :startDate and rental.endDate >= :endDate))")
    List<Rental> findForSpecifPeriod
            (@Param("id") Integer id, @Param("startDate") Date start, @Param("endDate") Date end);

    @Query(value = "select user.username from Rental rental join rental.apartment apartment" +
            " join apartment.user user where rental.id =:id")
    String ownerUsername(@Param("id") Integer id);
}