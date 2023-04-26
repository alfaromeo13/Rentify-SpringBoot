package com.example.rentify.repository;

import com.example.rentify.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select user from User user join fetch user.roles where user.id = :id")
    User findUserWithRoles(@Param("id") Integer id);

    @Query(value = "select user from User user left join fetch user.roles " +
            " where user.username = :username and user.isActive = true ")
    User findByUsername(@Param("username") String username);

    @Query(value = "select apartment.id from User user " +
            "join user.favoriteApartments apartment where user.username = :username")
    Page<Integer> favouriteApartmentsForUser(@Param("username") String username, Pageable pageable);

    boolean existsByUsername(String username);
}