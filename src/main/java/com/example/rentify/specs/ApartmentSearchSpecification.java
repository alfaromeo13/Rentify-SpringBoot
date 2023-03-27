package com.example.rentify.specs;

import com.example.rentify.entity.*;
import com.example.rentify.search.ApartmentSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.FetchType;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ApartmentSearchSpecification implements Specification<Apartment> {

    private final List<Integer> ids;
    private final ApartmentSearch apartmentSearch;

    @Override
    public Predicate toPredicate(Root<Apartment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();
        if (!query.getResultType().equals(Long.class)) {
            root.fetch("address");
            root.fetch("user");
            root.fetch("images");
            root.fetch("apartmentAttributes");
            //mapper makes get request in background for us when get() is called and therefore we first need to fetch
        }
        if (ids != null && !ids.isEmpty()) {   //filterByApartmentsIDs
            Predicate apartmentIdPredicate = root.get("id").in(ids);
            predicateList.add(apartmentIdPredicate);
        }
        filter(root, criteriaBuilder, predicateList);
        query.distinct(true);
        //We return one predicate by combining all predicates with AND
        // which acts as the WHERE condition for query satisfaction"
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }

    private void filter(Root<Apartment> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        Join<Apartment, Address> addressJoin = root.join("address", JoinType.LEFT);
        Join<Address, Neighborhood> neighborhoodJoin = addressJoin.join("neighborhood");
        Join<Neighborhood, City> cityJoin = neighborhoodJoin.join("city");
        Join<City, Country> countryJoin = cityJoin.join("country");
        Join<Apartment, User> userJoin = root.join("user", JoinType.LEFT);
        Join<Apartment, Rental> rentalJoin = root.join("rentals", JoinType.LEFT);
        Join<Apartment, ApartmentAttribute> apartmentAttributesJoin = root
                .join("apartmentAttributes", JoinType.LEFT);
        filterById(root, criteriaBuilder, predicateList);
        filterByPrice(root, criteriaBuilder, predicateList);
        filterByTitle(root, criteriaBuilder, predicateList);
        filterByDescription(root, criteriaBuilder, predicateList);
        filterByNumOfBathrooms(root, criteriaBuilder, predicateList);
        filterByAddressStreet(criteriaBuilder, predicateList, addressJoin);
        filterByCityName(criteriaBuilder, predicateList, cityJoin);
        filterBySquareMeters(root, criteriaBuilder, predicateList);
        filterByNeighborhoodName(criteriaBuilder, predicateList, neighborhoodJoin);
        filterByNumOfBedrooms(root, criteriaBuilder, predicateList);
        filterByCountryName(criteriaBuilder, predicateList, countryJoin);
        filterByUserId(criteriaBuilder, predicateList, userJoin);
        filterByAvailabilityDate(root, criteriaBuilder, predicateList, rentalJoin);
        filterByAttribute(criteriaBuilder, predicateList, apartmentAttributesJoin
                , "WiFi", apartmentSearch.getWiFi());
        filterByAttribute(criteriaBuilder, predicateList, apartmentAttributesJoin,
                "Air Conditioning", apartmentSearch.getAirConditioning());
        filterByAttribute(criteriaBuilder, predicateList, apartmentAttributesJoin,
                "Pool", apartmentSearch.getPool());
        filterByAttribute(criteriaBuilder, predicateList, apartmentAttributesJoin,
                "Furnished", apartmentSearch.getFurnished());
        filterByAttribute(criteriaBuilder, predicateList, apartmentAttributesJoin,
                "Balcony", apartmentSearch.getBalcony());
        filterByAttribute(criteriaBuilder, predicateList, apartmentAttributesJoin,
                "Pets Allowed", apartmentSearch.getPetsAllowed());
        filterByAttribute(criteriaBuilder, predicateList, apartmentAttributesJoin,
                "Parking", apartmentSearch.getParking());
        filterByAttribute(criteriaBuilder, predicateList, apartmentAttributesJoin,
                "Distance to public transport", apartmentSearch.getPublicTransportDist());
        filterByAttribute(criteriaBuilder, predicateList, apartmentAttributesJoin,
                "Appliances", apartmentSearch.getAppliances());
        filterByAttribute(criteriaBuilder, predicateList, apartmentAttributesJoin,
                "Elevator", apartmentSearch.getElevator());
    }

    private void filterByAvailabilityDate(Root<Apartment> root, CriteriaBuilder criteriaBuilder,
                                          List<Predicate> predicateList, Join<Apartment, Rental> rentalJoin) {
        if (apartmentSearch.getAvailableFrom() != null) {
            Predicate availableFromPredicate = criteriaBuilder.equal(
                    rentalJoin.get("startDate"), apartmentSearch.getAvailableFrom());
            predicateList.add(availableFromPredicate);
        }
        if (apartmentSearch.getAvailableTo() != null) {
            Predicate availableToPredicate = criteriaBuilder.equal(
                    root.get("endDate"), apartmentSearch.getAvailableTo());
            predicateList.add(availableToPredicate);
        }
    }

    private void filterByAttribute(CriteriaBuilder criteriaBuilder, List<Predicate> predicateList,
                                   Join<Apartment, ApartmentAttribute> apartmentAttributesJoin,
                                   String attributeName, String attributeValue) {
        if (attributeValue != null) {
            Predicate attributePredicate = criteriaBuilder.equal(
                    apartmentAttributesJoin.get("attribute"), new Attribute(attributeName));
            predicateList.add(attributePredicate);
            Predicate attributeValuePredicate = criteriaBuilder.equal(
                    apartmentAttributesJoin.get("attributeValue"), attributeValue);
            predicateList.add(attributeValuePredicate);
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

    private void filterByAddressStreet(CriteriaBuilder criteriaBuilder,
                                       List<Predicate> predicateList, Join<Apartment, Address> addressJoin) {
        if (apartmentSearch.getAddressStreet() != null) { //join example
            Predicate addressStreetPredicate = criteriaBuilder.equal(
                    addressJoin.get("street"), apartmentSearch.getAddressStreet());
            predicateList.add(addressStreetPredicate);
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