package com.example.rentify.repository;

import com.example.rentify.entity.Country;
import com.example.rentify.projections.CountryIdAndShortCodeProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    //metoda koja vrace sve drzave iz nase tabele i svaki red ce se mapirati u klasu country
    @Query(value = "select country from Country as country")
    List<Country> findAllCountries();

    //ako nam treba samo odredjeni set podataka npr id i short code pisemo interfejs
    @Query(value = "select country.id as id ,country.shortCode as shortCode from Country as country")
    List<CountryIdAndShortCodeProjection> findIdAndCodeUsingCustomProjection();

    @Query(value = "select country from Country as country")
    Page<Country> findAllJPQL(Pageable pageable);

    //prvi upit koji vrace id-eve drzava sa stranicenjem
    @Query(value = "select country.id from Country country")
    Page<Integer> findIdsPageable(Pageable pageable);

    @Query(value = "select distinct country from Country country " +
            "join fetch country.cities " +
            "where country.id in (:countryIds)")
    List<Country> findByIdIn(@Param("countryIds") List<Integer> countryIds);


    //OVAJ KORISTIMOOO
    //Yes, the JPQL query you provided should be optimal and efficient.
    // It uses the select clause to explicitly select only the necessary
    // fields (id and shortCode) from the Country entity, instead of
    // selecting all fields with the from clause. This can help reduce
    // the amount of data retrieved from the database and improve performance.
    //Also, the count function is used in the countQuery to count the
    // total number of Country entities, which is more efficient than
    // retrieving all entities and then counting them in memory.
    //Overall, this query should be a good choice for retrieving a
    // pageable list of Country entities with only the necessary fields,
    // while minimizing the number of queries executed against the database.
    @Query(value = "select c.id as id, c.shortCode as shortCode from Country c")
    Page<CountryIdAndShortCodeProjection> findAllPageable(Pageable pageable);
}