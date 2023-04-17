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
            "join address.neighborhood neighborhood where neighborhood.name like concat(:name,'%')")
    List<Address> findAllInNeighborhood(@Param("name") String neighborhoodName);

    // sve adrese po odredjenom postanskom broju
    @Query(value = "select address from Address address where address.code = :code")
    List<Address> findByCode(@Param("code") String postalCode);
}