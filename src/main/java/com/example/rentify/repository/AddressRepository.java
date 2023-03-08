package com.example.rentify.repository;

import com.example.rentify.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    //Metod koji vraca sve adrese po odredjenom postanskom broju (postal_code)
    @Query(value = "select address from Address as address where address.code = :code")
    List<Address> getByCode(@Param("code") String postalCode);

    //Metod koji vraca sve adrese koje pripadaju gradu sa odredjenim nazivom
    @Query(value = "select address from Address as address join address.city as city where city.name = :name")
    List<Address> getAllInCity(@Param("name") String cityName);

    //Metod koji vraca sve adrese koje pripadaju odredjenoj drzavi(po short codu)
    @Query(value = "select address from Address as address " +
            "join address.city as city join city.country as country where country.shortCode = :code")
    List<Address> getAllInCountry(@Param("code") String shortCode);

    //Metod koji vraca adresu po jedinstvenom identifikatoru ukljucujuci podatak o gradu
    @Query(value = "select address from Address as address join fetch address.city where address.id= :id")
    List<Address> getByIdWithCity(@Param("id") Integer id);
}