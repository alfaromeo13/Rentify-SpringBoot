package com.example.rentify.repository;

import com.example.rentify.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Integer>, JpaSpecificationExecutor<Apartment> {
    //nasljedjivanje vise interfejsa u javi je dozvoljeno ali klasa nije!

//JPQL upit koji povlaci id, ime korisnika, broj telefona i sve adrese na kojima su njegovi apartmani
//JPQL upit koji povlači sve apartmane čije adrese pripadaju određenom gradu (po imenu grada). Upit treba da vrati: ime izdavaca, email adresu, naziv grada, naziv drzave i naziv adrese. Za mapiranje iskoristiti projekciju (custom interfejs)
//JPQL upit sa straničenjem koji vraća sve apartmane čija adresa pripada državi (Veličina stranice treba da bude 15 npr)
}
