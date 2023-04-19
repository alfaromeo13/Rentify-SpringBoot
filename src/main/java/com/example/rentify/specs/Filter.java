package com.example.rentify.specs;

import com.example.rentify.entity.*;
import com.example.rentify.search.ApartmentSearch;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.List;

@Setter
@Component
public class Filter {

    private ApartmentSearch apartmentSearch;

    public void all(Root<Apartment> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        Join<Apartment, Address> addressJoin = root.join("address", JoinType.LEFT);
        Join<Address, Neighborhood> neighborhoodJoin = addressJoin.join("neighborhood", JoinType.LEFT);
        Join<Neighborhood, City> cityJoin = neighborhoodJoin.join("city", JoinType.LEFT);
        Join<City, Country> countryJoin = cityJoin.join("country", JoinType.LEFT);
        Join<Apartment, User> userJoin = root.join("user", JoinType.LEFT);
        Join<Apartment, Rental> rentalJoin = root.join("rentals", JoinType.LEFT);
        filterById(root, criteriaBuilder, predicateList);
        filterByPrice(root, criteriaBuilder, predicateList);
        filterByTitle(root, criteriaBuilder, predicateList);
        filterByNumOfBathrooms(root, criteriaBuilder, predicateList);
        filterByCityName(criteriaBuilder, predicateList, cityJoin);
        filterBySquareMeters(root, criteriaBuilder, predicateList);
        filterByNeighborhoodName(criteriaBuilder, predicateList, neighborhoodJoin);
        filterByNumOfBedrooms(root, criteriaBuilder, predicateList);
        filterByCountryName(criteriaBuilder, predicateList, countryJoin);
        filterByUserId(criteriaBuilder, predicateList, userJoin);
        filterByAvailabilityDate(root, criteriaBuilder, predicateList, rentalJoin);
        filterByAttribute(criteriaBuilder, predicateList, root
                , "WiFi", apartmentSearch.getWiFi());
        filterByAttribute(criteriaBuilder, predicateList, root,
                "Air Conditioning", apartmentSearch.getAirConditioning());
        filterByAttribute(criteriaBuilder, predicateList, root,
                "Pool", apartmentSearch.getPool());
        filterByAttribute(criteriaBuilder, predicateList, root,
                "Furnished", apartmentSearch.getFurnished());
        filterByAttribute(criteriaBuilder, predicateList, root,
                "Balcony", apartmentSearch.getBalcony());
        filterByAttribute(criteriaBuilder, predicateList, root,
                "Pets Allowed", apartmentSearch.getPetsAllowed());
        filterByAttribute(criteriaBuilder, predicateList, root,
                "Parking", apartmentSearch.getParking());
        filterByAttribute(criteriaBuilder, predicateList, root,
                "Distance to public transport", apartmentSearch.getPublicTransportDist());
        filterByAttribute(criteriaBuilder, predicateList, root,
                "Appliances", apartmentSearch.getAppliances());
        filterByAttribute(criteriaBuilder, predicateList, root,
                "Elevator", apartmentSearch.getElevator());
    }

    private void filterByAvailabilityDate(Root<Apartment> root, CriteriaBuilder criteriaBuilder,
                                          List<Predicate> predicateList, Join<Apartment, Rental> rentalJoin) {
        //logika treba da bude da iz rentala uzme sve datume koji su razliciti od ovih vidji radi li ovo sto si napravio
        if (apartmentSearch.getAvailableFrom() != null) {
            Predicate availableFromPredicate = criteriaBuilder.notEqual(
                    rentalJoin.get("startDate"), apartmentSearch.getAvailableFrom());
            predicateList.add(availableFromPredicate);
        }
        if (apartmentSearch.getAvailableTo() != null) {
            Predicate availableToPredicate = criteriaBuilder.notEqual(
                    root.get("endDate"), apartmentSearch.getAvailableTo());
            predicateList.add(availableToPredicate);
        }
    }

    private void filterByAttribute(CriteriaBuilder criteriaBuilder, List<Predicate> predicateList,
                                   Root<Apartment> root, String attributeName, String attributeValue) {
        if (attributeValue != null) {
            Join<Apartment, ApartmentAttribute> apartmentAttributesJoin = root
                    .join("apartmentAttributes", JoinType.LEFT);
            Join<ApartmentAttribute, Attribute> attributeJoin = apartmentAttributesJoin.join("attribute", JoinType.LEFT);
            Predicate attributePredicate = criteriaBuilder.and(
                    criteriaBuilder.equal(attributeJoin.get("name"), attributeName)
                    , criteriaBuilder.equal(apartmentAttributesJoin.get("attributeValue"), attributeValue));
            predicateList.add(attributePredicate);
        }
    }

    private void filterByUserId(CriteriaBuilder criteriaBuilder, List<Predicate> predicateList,
                                Join<Apartment, User> userJoin) {
        //get all apartments for specific user(when user logs and want to check his apartments)
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

    private void filterByCountryName(CriteriaBuilder criteriaBuilder, List<Predicate> predicateList,
                                     Join<City, Country> countryJoin) {
        if (apartmentSearch.getCountryName() != null) {
            Predicate countryNamePredicate = criteriaBuilder.like(
                    countryJoin.get("name"), apartmentSearch.getCountryName() + "%");
            predicateList.add(countryNamePredicate);
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