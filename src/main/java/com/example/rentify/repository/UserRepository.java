package com.example.rentify.repository;

import com.example.rentify.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select user from User user join fetch user.roles where user.id = :id")
    User userWithRoles(@Param("id") Integer id);
}