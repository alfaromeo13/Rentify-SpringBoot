package com.example.rentify.repository;

import com.example.rentify.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status, String> {
    @Query(value="select status.name from Status status")
    List<String> findAllStatuses();
}
