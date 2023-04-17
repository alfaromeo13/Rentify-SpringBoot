package com.example.rentify.repository;

import com.example.rentify.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    @Query(value = "select city.id from City city where city.name like concat(:name , '%')")
    Page<Integer> findIdsPageable(Pageable pageable, @Param("name") String name);

    @Query(value = "select distinct city from City city join fetch city.country where city.id in (:cityIds)")
    List<City> findByNameStartingWith(@Param("cityIds") List<Integer> cityIds);

    @Query(value = "select city from City city join city.country country " +
            "where country.name like concat(:countryName , '%') and city.name like concat(:cityName , '%')")
    Page<City> findAllCitiesFromCountryNameJPQL(@Param("countryName") String countryName,
                                                @Param("cityName") String cityName, Pageable pageable);
}