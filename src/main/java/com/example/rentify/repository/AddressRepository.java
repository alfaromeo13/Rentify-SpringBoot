package com.example.rentify.repository;

import com.example.rentify.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    //sve adrese koje pripadaju naselju sa odredjenim nazivom
    @Query(value = "select address from Address address " +
            "join address.neighborhood neighborhood where neighborhood.name like :name%")
    List<Address> getAllInNeighborhood(@Param("name") String neighborhoodName);

    // sve adrese po odredjenom postanskom broju
    @Query(value = "select address from Address address where address.code = :code")
    List<Address> getByCode(@Param("code") String postalCode);

    //sve adrese koje pripadaju gradu sa odredjenim nazivom
    @Query(value = "select address from Address address " +
            "join address.neighborhood neighborhood " +
            "join neighborhood.city city where city.name like :name%")
    List<Address> getAllInCity(@Param("name") String cityName);

    //sve adrese koje pripadaju odredjenoj drzavi(name)
    @Query(value = "select address from Address address " +
            "join address.neighborhood neighborhood " +
            "join neighborhood.city city " +
            "join city.country country where country.name like :name%")
    List<Address> getAllInCountryByName(@Param("name") String name);

    //sve adrese koje pripadaju drzavi(short_code)
    @Query(value = "select address from Address address " +
            "join address.neighborhood neighborhood " +
            "join neighborhood.city city " +
            "join city.country country where country.shortCode like :code%")
    List<Address> getAllInCountryByCode(@Param("code") String shortCode);
}