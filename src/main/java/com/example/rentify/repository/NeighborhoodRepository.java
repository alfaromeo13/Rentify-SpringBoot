package com.example.rentify.repository;

import com.example.rentify.entity.Neighborhood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NeighborhoodRepository extends JpaRepository<Neighborhood, Integer> {
    @Query(value = "select neighborhood from Neighborhood neighborhood" +
            " join neighborhood.city city where city.name like concat(:cityName,'%')" +
            " and neighborhood.name like concat(:neighborhoodName,'%')")
    Page<Neighborhood> findByCityAndNeighborhood(String cityName, String neighborhoodName, Pageable pageable);
}