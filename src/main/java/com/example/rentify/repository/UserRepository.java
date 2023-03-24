package com.example.rentify.repository;

import com.example.rentify.entity.Apartment;
import com.example.rentify.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select user from User user join fetch user.roles where user.id = :id")
    User findUserWithRoles(@Param("id") Integer id);

    @Query(value = "select apartment from User user " +
            "join user.favoriteApartments apartment where user.id = :id")
    Page<Apartment> favouriteApartmentsForUserById(@Param("id") Integer id, Pageable pageable);
}