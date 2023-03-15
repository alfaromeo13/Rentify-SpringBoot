package com.example.rentify.repository;

import com.example.rentify.dto.UserWithRolesDTO;
import com.example.rentify.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select user from User user join fetch user.roles where user.id = :id")
    User userWithRoles(@Param("id") Integer id);

    @Query(value = "select user from User user join fetch user.roles where user.id = :id")
    UserWithRolesDTO userWithRolesDTO(@Param("id") Integer id);

    @Query(value = "select user from User user left join fetch user.roles where user.id = :id")
    Optional<User> findWithRolesById(@Param("id") Integer id);
}