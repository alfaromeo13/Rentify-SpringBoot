package com.example.rentify.specs;

import com.example.rentify.entity.*;
import com.example.rentify.search.ApartmentSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ApartmentSearchSpecification implements Specification<Apartment> {

    private final ApartmentSearch apartmentSearch;

    @Override
    public Predicate toPredicate(Root<Apartment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();

        filterByPrice(root, criteriaBuilder, predicateList);
        filterByDescription(root, criteriaBuilder, predicateList);
        filterByNumber(root, criteriaBuilder, predicateList);
        filterByNumOfBathrooms(root, criteriaBuilder, predicateList);
        filterByAddressStreet(criteriaBuilder, predicateList, root);
        filterByCityName(criteriaBuilder, predicateList, root);

        //We return one predicate by combining all predicates with AND
        // which acts as the WHERE condition for query satisfaction"
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }

//ako treba da pretrazimo po drugim dodatnim poljima moramo ih dodati u klasi ApartmentSearch

    private void filterByCityName(
            CriteriaBuilder criteriaBuilder, List<Predicate> predicateList, Root<Apartment> root) {
        if (apartmentSearch.getCityName() != null) {
            Join<Apartment, Address> addressJoin = root.join("address", JoinType.LEFT);
            Join<Address, City> cityJoin = addressJoin.join("city");
            // Join<City, Country> countryJoin = cityJoin.join("country");
            // da smo htjeli da prosirimo filtirranje po drzvai dodali bi jos jedan join
            // na country
            Predicate cityNamePredicate = criteriaBuilder.equal(
                    cityJoin.get("name"), apartmentSearch.getCityName());
            predicateList.add(cityNamePredicate);
        }
    }

    private void filterByAddressStreet(CriteriaBuilder criteriaBuilder,
                                       List<Predicate> predicateList, Root<Apartment> root) {
        if (apartmentSearch.getAddressStreet() != null) { //join example
            Join<Apartment, Address> addressJoin = root.join("address", JoinType.LEFT);
            Predicate addressStreetPredicate = criteriaBuilder.equal(
                    addressJoin.get("street"), apartmentSearch.getAddressStreet());
            predicateList.add(addressStreetPredicate);
            //join Apartment.address ...
        }
    }

    private void filterByDescription(Root<Apartment> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        if (apartmentSearch.getDescription() != null) {
            Predicate descriptionPredicate = criteriaBuilder.like(root.get("description"), "%" + apartmentSearch.getDescription() + "%");
            predicateList.add(descriptionPredicate);
            //... where apartments.description like '%some description%'
        }
    }

    private void filterByNumOfBathrooms(Root<Apartment> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        if (apartmentSearch.getNumOfBathrooms() != null) {
            Predicate numOfBathroomsPredicate = criteriaBuilder.equal(root.get("numOfBathrooms"), apartmentSearch.getNumOfBathrooms());
            predicateList.add(numOfBathroomsPredicate);
            //... where apartments.numOfBathrooms = :numOfBathrooms
        }
    }

    private void filterByNumber(Root<Apartment> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        if (apartmentSearch.getNumber() != null) {
            Predicate numberPredicate = criteriaBuilder.like(root.get("number"), "%" + apartmentSearch.getNumber() + "%");
            predicateList.add(numberPredicate);
            //... where apartments.number like '%555757%';
        }
    }

    private void filterByPrice(Root<Apartment> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        if (apartmentSearch.getPrice() != null) {
            Predicate pricePredicate = criteriaBuilder.equal(root.get("price"), apartmentSearch.getPrice());
            predicateList.add(pricePredicate);
            //... where apartments.price = :price
        }
    }
}