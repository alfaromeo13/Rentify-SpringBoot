package com.example.rentify.repository;

import com.example.rentify.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    //select * form role where name=""
    List<Role> findByName(String name);

    //select * from role where name like '?%'
    List<Role> findByNameStartingWith(String startName);

    //select * from role where name like '%?%'
    List<Role> findByNameContaining(String word);

    //JPQL upiti
    @Query(value = "select role from Role as role where role.name = :name")
    List<Role> findAllByNameJPQL(@Param("name") String name);

    @Query(value = "select role.name from Role as role ")
    List<String> findAllNamesJPQL(); //povratna vrijednost je string

}