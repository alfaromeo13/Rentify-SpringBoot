package com.example.rentify.repository;

import com.example.rentify.entity.Neighborhood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NeighborhoodRepository extends JpaRepository<Neighborhood, Integer> {
    @Query(value = "select neighborhood from Neighborhood neighborhood" +
            " join neighborhood.city city where city.name like concat(:cityName,'%')" +
            " and neighborhood.name like concat(:neighborhoodName,'%')")
    List<Neighborhood> findByCityAndNeighborhood(String cityName, String neighborhoodName);
}