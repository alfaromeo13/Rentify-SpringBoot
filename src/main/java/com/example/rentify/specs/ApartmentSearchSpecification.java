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
        Join<Apartment, Address> addressJoin = root.join("address", JoinType.LEFT);
        Join<Address, Neighborhood> neighborhoodJoin = addressJoin.join("neighborhood");
        Join<Neighborhood, City> cityJoin = neighborhoodJoin.join("city");
        Join<City, Country> countryJoin = cityJoin.join("country");
        Join<Apartment, User> userJoin = root.join("user", JoinType.LEFT);

        filterById(root, criteriaBuilder, predicateList);
        filterByPrice(root, criteriaBuilder, predicateList);
        filterByTitle(root, criteriaBuilder, predicateList);
        filterByDescription(root, criteriaBuilder, predicateList);
        filterByNumber(root, criteriaBuilder, predicateList);
        filterByNumOfBathrooms(root, criteriaBuilder, predicateList);
        filterByAddressStreet(criteriaBuilder, predicateList, addressJoin);
        filterByCityName(criteriaBuilder, predicateList, cityJoin);
        filterBySquareMeters(root, criteriaBuilder, predicateList);
        filterByNeighborhoodName(criteriaBuilder, predicateList, neighborhoodJoin);
        filterByNumOfBedrooms(root, criteriaBuilder, predicateList);
        filterByCountryName(criteriaBuilder, predicateList, countryJoin);
        filterByUserId(criteriaBuilder, predicateList, userJoin);

        //We return one predicate by combining all predicates with AND
        // which acts as the WHERE condition for query satisfaction"
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }

    private void filterByUserId(CriteriaBuilder criteriaBuilder, List<Predicate> predicateList, Join<Apartment, User> userJoin) {
        //get all apartments for specific user
        if (apartmentSearch.getUserId() != null) {
            Predicate userIdPredicate = criteriaBuilder.equal(
                    userJoin.get("id"), apartmentSearch.getUserId());
            predicateList.add(userIdPredicate);
        }
    }

    private void filterById(Root<Apartment> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        if (apartmentSearch.getId() != null) {
            Predicate idPredicate = criteriaBuilder.equal(root.get("id"), apartmentSearch.getId());
            predicateList.add(idPredicate);
        }
    }

    private void filterByCountryName(CriteriaBuilder criteriaBuilder, List<Predicate> predicateList, Join<City, Country> countryJoin) {
        if (apartmentSearch.getCountryName() != null) {
            Predicate countryNamePredicate = criteriaBuilder.like(
                    countryJoin.get("name"), apartmentSearch.getCountryName() + "%");
            predicateList.add(countryNamePredicate);
        }
    }

    private void filterByAddressStreet(CriteriaBuilder criteriaBuilder,
                                       List<Predicate> predicateList, Join<Apartment, Address> addressJoin) {
        if (apartmentSearch.getAddressStreet() != null) { //join example
            Predicate addressStreetPredicate = criteriaBuilder.equal(
                    addressJoin.get("street"), apartmentSearch.getAddressStreet());
            predicateList.add(addressStreetPredicate);
            //join Apartment.address ...
        }
    }

    private void filterByNeighborhoodName(CriteriaBuilder criteriaBuilder,
                                          List<Predicate> predicateList, Join<Address, Neighborhood> neighborhoodJoin) {
        if (apartmentSearch.getNeighborhoodName() != null) {//join example
            Predicate neighborhoodNamePredicate = criteriaBuilder.equal(
                    neighborhoodJoin.get("name"), apartmentSearch.getNeighborhoodName());
            predicateList.add(neighborhoodNamePredicate);
        }
    }

    private void filterByCityName(CriteriaBuilder criteriaBuilder,
                                  List<Predicate> predicateList, Join<Neighborhood, City> cityJoin) {
        if (apartmentSearch.getCityName() != null) {//join example
            Predicate cityNamePredicate = criteriaBuilder.equal(
                    cityJoin.get("name"), apartmentSearch.getCityName());
            predicateList.add(cityNamePredicate);
        }
    }

    private void filterByDescription(Root<Apartment> root,
                                     CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        if (apartmentSearch.getDescription() != null) {
            Predicate descriptionPredicate = criteriaBuilder.like(
                    root.get("description"), "%" + apartmentSearch.getDescription() + "%");
            predicateList.add(descriptionPredicate);
            //... where apartments.description like '%some description%'
        }
    }

    private void filterByTitle(Root<Apartment> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        if (apartmentSearch.getTitle() != null) {
            Predicate titlePredicate = criteriaBuilder.like(
                    root.get("title"), "%" + apartmentSearch.getTitle() + "%");
            predicateList.add(titlePredicate);
            //... where apartments.title like '%title%'
        }
    }

    private void filterByNumOfBathrooms(Root<Apartment> root,
                                        CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        if (apartmentSearch.getMinNumOfBathrooms() != null) {
            Predicate minNumOfBathroomsPredicate = criteriaBuilder.greaterThanOrEqualTo(
                    root.get("numOfBathrooms"), apartmentSearch.getMinNumOfBathrooms());
            predicateList.add(minNumOfBathroomsPredicate);
            //... where apartments.numOfBathrooms >= :numOfBathrooms
        }
        if (apartmentSearch.getMaxNumOfBathrooms() != null) {
            Predicate maxNumOfBathroomsPredicate = criteriaBuilder.lessThanOrEqualTo(
                    root.get("numOfBathrooms"), apartmentSearch.getMaxNumOfBathrooms());
            predicateList.add(maxNumOfBathroomsPredicate);
            //... where apartments.numOfBathrooms <=:numOfBathrooms
        }
    }

    private void filterByNumber(Root<Apartment> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        if (apartmentSearch.getNumber() != null) {
            Predicate numberPredicate = criteriaBuilder.like(
                    root.get("number"), "%" + apartmentSearch.getNumber() + "%");
            predicateList.add(numberPredicate);
            //... where apartments.number like '%555757%';
        }
    }

    private void filterBySquareMeters(Root<Apartment> root,
                                      CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        if (apartmentSearch.getMinSquareMeters() != null) {
            Predicate minSquarePredicate = criteriaBuilder.greaterThanOrEqualTo(
                    root.get("sqMeters"), apartmentSearch.getMinSquareMeters());
            predicateList.add(minSquarePredicate);
            //... where apartments.sqMeters >= :sqMeters
        }
        if (apartmentSearch.getMaxSquareMeters() != null) {
            Predicate maxSquarePredicate = criteriaBuilder.lessThanOrEqualTo(
                    root.get("sqMeters"), apartmentSearch.getMaxSquareMeters());
            predicateList.add(maxSquarePredicate);
            //... where apartments.sqMeters <= :sqMeters
        }
    }

    private void filterByPrice(Root<Apartment> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        if (apartmentSearch.getMinPrice() != null) {
            Predicate minPricePredicate = criteriaBuilder.greaterThanOrEqualTo(
                    root.get("price"), apartmentSearch.getMinPrice());
            predicateList.add(minPricePredicate);
            //... where apartments.price >= :price
        }
        if (apartmentSearch.getMaxPrice() != null) {
            Predicate maxPricePredicate = criteriaBuilder.lessThanOrEqualTo(
                    root.get("price"), apartmentSearch.getMaxPrice());
            predicateList.add(maxPricePredicate);
            //... where apartments.price <= :price
        }
    }

    private void filterByNumOfBedrooms(Root<Apartment> root,
                                       CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        if (apartmentSearch.getMinNumOfBedrooms() != null) {
            Predicate minNumOfBedroomsPredicate = criteriaBuilder.greaterThanOrEqualTo(
                    root.get("numOfBedrooms"), apartmentSearch.getMinNumOfBedrooms());
            predicateList.add(minNumOfBedroomsPredicate);
            //... where apartments.numOfBedrooms >= :numOfBedrooms
        }
        if (apartmentSearch.getMaxNumOfBedrooms() != null) {
            Predicate maxNumOfBedroomsPredicate = criteriaBuilder.lessThanOrEqualTo(
                    root.get("numOfBedrooms"), apartmentSearch.getMaxNumOfBedrooms());
            predicateList.add(maxNumOfBedroomsPredicate);
            //... where apartments.numOfBedrooms <=:numOfBedrooms
        }
    }
}