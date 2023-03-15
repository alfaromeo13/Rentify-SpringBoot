package com.example.rentify.specs;

import com.example.rentify.entity.Address;
import com.example.rentify.entity.Apartment;
import com.example.rentify.entity.City;
import com.example.rentify.entity.Country;
import com.example.rentify.search.ApartmentSearch;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class ApartmentSearchSpecification implements Specification<Apartment> {

    private ApartmentSearch apartmentSearch;

    public ApartmentSearchSpecification(ApartmentSearch apartmentSearch) {
        this.apartmentSearch = apartmentSearch;//sada u gornjoj referenci imamo sve moguce query parametre
    }

    @Override //pozivanje ove metode radi hibernate za nas
    public Predicate toPredicate(Root<Apartment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();
        Join<Apartment, Address> addressJoin = fetchAll(root);

        filterByPrice(root, criteriaBuilder, predicateList);
        filterByDescription(root, criteriaBuilder, predicateList);
        filterByNumber(root, criteriaBuilder, predicateList);
        filterByNumOfBathrooms(root, criteriaBuilder, predicateList);
        filterByAddressStreet(criteriaBuilder, predicateList, addressJoin);

//        if (apartmentSearch.getCityName() != null) {
//            Join<Apartment, Address> addressJoin = root.join("address");
//            Join<Address, City> cityJoin = addressJoin.join("city");
//            // Join<City, Country> countryJoin = cityJoin.join("country");
//            Predicate cityNamePredicate = criteriaBuilder.equal(
//                    cityJoin.get("name"), apartmentSearch.getCityName());
//            predicateList.add(cityNamePredicate);
//
//            //ovim vidimo da ne postoji MAPIRANJE(u entioty klasi) izmedju entiteta (npr da nemamo adresu u Apartman
//            //entitetu ne bi mogli stici do adresa nikako.(ni preko JOA method querija,ni JPQL-a ni sl.)
//        }

        //vracemo 1 predikat koji dobijamo tako sto izmedju svih predikata stavimo AND(svi poslati queriji
        // moraju biti zadovoljeni)
        return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
        //ovaj finalni predikate ce biti WHERE USLOV KOJI KREIRA and operastor izmedju svakog predikata ako psotoji
        //kod or operacije ako je prvi uslov zadovoljen ide se na drugi uslov i nacrtaj onaj upit u svesku

    }

    private Join<Apartment, Address> fetchAll(Root<Apartment> root) {
        Fetch<Apartment, Address> addressFetch = root.fetch("address");
        //        Fetch<Address, City> cityFetch = addressJoin.fetch("city");
//        Join<Address, City> cityJoin = (Join<Address, City>) cityFetch;
//        Fetch<City, Country> countryFetch = cityJoin.fetch("country");
//        Join<City, Country> countryJoin = (Join<City, Country>) countryFetch;
        return (Join<Apartment, Address>) addressFetch;
    }

    private void filterByAddressStreet(CriteriaBuilder criteriaBuilder, List<Predicate> predicateList, Join<Apartment, Address> addressJoin) {
        if (apartmentSearch.getAddressStreet() != null) { //primjer kako pravimo join
            //u klasi Apartment imamo vec mapiranu relaciju i do adresa se stize preko polja
            //address

            //posto zelimo da vratimo apartman sa adresom moramo deifnisati join fetch
            //a ako zelimo npr apartman bez adresa onda bi morali da klijentu vratimo ApartmentDTO
            //klasu koja nema polje adresa u sebi!!!
            //recimo da moramo da napravim ojoin fetch


            //i sada koristimo ovaj address join

            //join parametrei govore SA koje NA koju tabelu radimo join
            //                  root je Apartment entitet i kazemo po kojem polju radimo join
            //********** da nam ne treba fetch ne bi imali addressFecth i ovaj gornji addressJoin
            // vec bi koristili ovaj join ispod(dakle ovaj koristimo ako nam ne treba fetch)
            //Join<Apartment, Address> addressJoin = root.join("address");//imamo pristup adrese za apartman
            //i sada mozmeo pristupiti poljima iz Address entiteta i mozemo napraviti predikat
            //isto bi pravili ako bi mapirali za manyToMany imali bi pristup adresama u gradu npr da pravimo za to
            //ili pristup adresama vezanim za klijenta(dakle isto se pise)
            //ovaj parametar prvi je naziv polja iz Address tabele

            Predicate addressStreetPredicate = criteriaBuilder.equal(
                    addressJoin.get("street"), apartmentSearch.getAddressStreet());
            predicateList.add(addressStreetPredicate);

            //da smo htjeli da prosirimo na join na filtriranje po gradu isto npr,onda bi
            //sa adrese pravili join na grad (pa sa grada na drzavu ako treba i sl) i ponovili
            //postupak odozgo primejr ispod u if-u
        }
    }

    private void filterByDescription(Root<Apartment> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        if (apartmentSearch.getDescription() != null) {
            Predicate descriptionPredicate = criteriaBuilder.equal(root.get("description"), apartmentSearch.getDescription());
            predicateList.add(descriptionPredicate);
        }
    }

    private void filterByNumOfBathrooms(Root<Apartment> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        if (apartmentSearch.getNumOfBathrooms() != null) {
            Predicate numOfBathroomsPredicate = criteriaBuilder.equal(root.get("numOfBathrooms"), apartmentSearch.getNumOfBathrooms());
            predicateList.add(numOfBathroomsPredicate);
        }
    }

    private void filterByNumber(Root<Apartment> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        if (apartmentSearch.getNumber() != null) {
            Predicate numberPredicate = criteriaBuilder.like(root.get("number"), "%" + apartmentSearch.getNumber() + "%");
            predicateList.add(numberPredicate);
            //ovo iznad se prevodi u ovaj upit:  where apartments.number like '%555757%';
        }
    }

    private void filterByPrice(Root<Apartment> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        if (apartmentSearch.getPrice() != null) {//ako nam je proslijedjen ovaj query
            Predicate pricePredicate = criteriaBuilder.equal(root.get("price"), apartmentSearch.getPrice());
            //ovo zinad pravi WHERE apartments.price = :price
            //za operacije koristimo criteria builder metode .equal je za = i ta metoda prima 2 parametra
            //lijevi je polje iz nase originalne tabele a desni je sa cime ga poredimo

            //na kraju trebamo da  ovaj predikat dodamo u listu predikata
            predicateList.add(pricePredicate);

        }
    }
}
