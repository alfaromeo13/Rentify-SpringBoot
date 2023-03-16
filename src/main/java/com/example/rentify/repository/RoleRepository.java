package com.example.rentify.repository;

import com.example.rentify.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query(value = "select role.name from Role role")
    List<String> findAllNamesJPQL();
}