package com.example.rentify.specs;

import com.example.rentify.entity.*;
import com.example.rentify.search.ApartmentSearch;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Component
public class Filter {

    private ApartmentSearch apartmentSearch;

    public void all(Root<Apartment> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        Join<Apartment, Address> addressJoin = root.join("address", JoinType.LEFT);
        Join<Address, Neighborhood> neighborhoodJoin = addressJoin.join("neighborhood", JoinType.LEFT);
        Join<Neighborhood, City> cityJoin = neighborhoodJoin.join("city", JoinType.LEFT);
        Join<Apartment, PropertyType> typeJoin = root.join("propertyType", JoinType.LEFT);
        Join<City, Country> countryJoin = cityJoin.join("country", JoinType.LEFT);
        Join<Apartment, User> userJoin = root.join("user", JoinType.LEFT);
        Join<Apartment, Rental> rentalJoin = root.join("rentals", JoinType.LEFT);
        Join<Apartment, Period> periodJoin = root.join("period", JoinType.LEFT);
        filterById(root, predicateList);
        filterByPropertyType(predicateList, typeJoin);
        filterByPrice(root, criteriaBuilder, predicateList);
        filterByNumOfBathrooms(root, criteriaBuilder, predicateList);
        filterByCityName(criteriaBuilder, predicateList, cityJoin);
        filterBySquareMeters(root, criteriaBuilder, predicateList);
        filterByNeighborhoodName(criteriaBuilder, predicateList, neighborhoodJoin);
        filterByNumOfBedrooms(root, criteriaBuilder, predicateList);
        filterByCountryName(criteriaBuilder, predicateList, countryJoin);
        filterByCountryCode(criteriaBuilder, predicateList, countryJoin);
        filterByUsername(criteriaBuilder, predicateList, userJoin);
        filterByAvailabilityDate(criteriaBuilder, predicateList, rentalJoin);
        filterByAttribute(criteriaBuilder, predicateList, root
                , "WiFi", apartmentSearch.getWiFi());
        filterByAttribute(criteriaBuilder, predicateList, root,
                "Air Conditioning", apartmentSearch.getAirConditioning());
        filterByAttribute(criteriaBuilder, predicateList, root,
                "Furnished", apartmentSearch.getFurnished());
        filterByAttribute(criteriaBuilder, predicateList, root,
                "Balcony", apartmentSearch.getBalcony());
        filterByAttribute(criteriaBuilder, predicateList, root,
                "Pets Allowed", apartmentSearch.getPetsAllowed());
        filterByAttribute(criteriaBuilder, predicateList, root,
                "Parking", apartmentSearch.getParking());
        filterByAttribute(criteriaBuilder, predicateList, root,
                "Appliances", apartmentSearch.getAppliances());
        filterByAttribute(criteriaBuilder, predicateList, root,
                "Elevator", apartmentSearch.getElevator());
        filterByPeriod(predicateList, periodJoin);
        filterByActive(root, userJoin, criteriaBuilder, predicateList);
        filterByNotApproved(root,criteriaBuilder,predicateList);
    }

    private void filterByCountryCode(CriteriaBuilder criteriaBuilder, List<Predicate> predicateList, Join<City, Country> countryJoin) {
        if (apartmentSearch.getCountryCode() != null) {
            Predicate countryCodePredicate = criteriaBuilder.like(
                    countryJoin.get("shortCode"), apartmentSearch.getCountryCode() + "%");
            predicateList.add(countryCodePredicate);
        }
    }

    private void filterByPropertyType(List<Predicate> predicateList, Join<Apartment, PropertyType> typeJoin) {
        if (!apartmentSearch.getType().isEmpty()) {
            List<String> types = apartmentSearch.getType();
            Predicate typePredicate = typeJoin.get("name").in(types);
            predicateList.add(typePredicate);
        }
    }

    private void filterByActive(Root<Apartment> root, Join<Apartment, User> userJoin,
                                CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        if(apartmentSearch.getIsActive() != null) {
            Predicate approvedApartments = criteriaBuilder.isTrue(root.get("isApproved"));
            predicateList.add(approvedApartments);// we filter only approved apartments!
            //if user is admin we return everything without filtering
            if (!apartmentSearch.getIsActive() && userHasAdminRole()) return;

            Predicate activeApartment = criteriaBuilder.isTrue(root.get("isActive"));
            predicateList.add(activeApartment);// we filter only active apartments!
            Predicate activeUser = criteriaBuilder.isTrue(userJoin.get("isActive"));
            predicateList.add(activeUser);// also we filter only active users!
        }
    }

    private void filterByNotApproved(Root<Apartment> root, CriteriaBuilder criteriaBuilder,
                                     List<Predicate> predicateList) {
        if(apartmentSearch.getIsApproved() != null && apartmentSearch.getIsActive() == null) {
            if (!apartmentSearch.getIsApproved() && userHasAdminRole()) {
                Predicate approvedApartments = criteriaBuilder.isFalse(root.get("isApproved"));
                predicateList.add(approvedApartments);// we filter only  not approved apartments!
            }
        }
    }

    private boolean userHasAdminRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //thread local
        // Loop through user's authorities to check for "admin" role
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_ADMIN"))
                return true;
        }
        return false;
    }

    private void filterByPeriod(List<Predicate> predicateList,
                                Join<Apartment, Period> periodJoin) {
        if (!apartmentSearch.getPeriod().isEmpty()) {
            List<String> periods = apartmentSearch.getPeriod();
            Predicate periodPredicate = periodJoin.get("name").in(periods);
            predicateList.add(periodPredicate);
        }
    }

    private void filterByAvailabilityDate(CriteriaBuilder criteriaBuilder,
                                          List<Predicate> predicateList, Join<Apartment, Rental> rentalJoin) {
        if (apartmentSearch.getAvailableFrom() != null && apartmentSearch.getAvailableTo() != null) {
            Predicate period = criteriaBuilder.and(
                    criteriaBuilder.lessThan(rentalJoin.get("startDate"), apartmentSearch.getAvailableTo()),
                    criteriaBuilder.greaterThan(rentalJoin.get("endDate"), apartmentSearch.getAvailableFrom()));
            Predicate occupiedForPeriodPredicate = criteriaBuilder
                    .and(criteriaBuilder.equal(rentalJoin.get("status"), new Status()), period);
            Predicate unoccupiedForPeriodPredicate = criteriaBuilder.or(
                    criteriaBuilder.isNull(rentalJoin.get("id")),
                    criteriaBuilder.not(occupiedForPeriodPredicate));
            //if apartment with that id is not in 'rentals' table meaning it is not rented yet or it is rented
            //and we check if it isn't rented for some specified period(not in "occupiedForPeriodPredicate")
            predicateList.add(unoccupiedForPeriodPredicate);
        }
    }

    private void filterByAttribute(CriteriaBuilder criteriaBuilder, List<Predicate> predicateList,
                                   Root<Apartment> root, String attributeName, String attributeValue) {
        if (attributeValue != null && !attributeValue.trim().isEmpty()) {
            Join<Apartment, ApartmentAttribute> apartmentAttributesJoin = root
                    .join("apartmentAttributes", JoinType.LEFT);
            Join<ApartmentAttribute, Attribute> attributeJoin =
                    apartmentAttributesJoin.join("attribute", JoinType.LEFT);
            Predicate attributePredicate = criteriaBuilder.and(
                    criteriaBuilder.equal(attributeJoin.get("name"), attributeName)
                    , criteriaBuilder.equal(apartmentAttributesJoin.get("attributeValue"), attributeValue));
            predicateList.add(attributePredicate);
        }
    }

    private void filterByUsername(CriteriaBuilder criteriaBuilder, List<Predicate> predicateList,
                                Join<Apartment, User> userJoin) {
        //get all apartments for specific user(when user logs and want to check his apartments)
        if (apartmentSearch.getUsername() != null) {
            Predicate usernamePredicate = criteriaBuilder.like(
                    userJoin.get("username"), apartmentSearch.getUsername()+ "%");
            predicateList.add(usernamePredicate);
        }
    }

    private void filterById(Root<Apartment> root, List<Predicate> predicateList) {
        if (!apartmentSearch.getId().isEmpty()) {
            List<Integer> ids = apartmentSearch.getId();
            Predicate idPredicate = root.get("id").in(ids);
            predicateList.add(idPredicate);
        }
    }

    private void filterByCountryName(CriteriaBuilder criteriaBuilder, List<Predicate> predicateList,
                                     Join<City, Country> countryJoin) {
        if (apartmentSearch.getCountryName() != null && !apartmentSearch.getCountryName().trim().isEmpty()) {
            Predicate countryNamePredicate = criteriaBuilder.like(
                    countryJoin.get("name"), apartmentSearch.getCountryName() + "%");
            predicateList.add(countryNamePredicate);
        }
    }

    private void filterByNeighborhoodName(CriteriaBuilder criteriaBuilder,
                                          List<Predicate> predicateList, Join<Address, Neighborhood> neighborhoodJoin) {
        if (apartmentSearch.getNeighborhoodName() != null && !apartmentSearch.getNeighborhoodName().trim().isEmpty()) {
            Predicate neighborhoodNamePredicate = criteriaBuilder.like(
                    neighborhoodJoin.get("name"), apartmentSearch.getNeighborhoodName()+ "%");
            predicateList.add(neighborhoodNamePredicate);
        }
    }

    private void filterByCityName(CriteriaBuilder criteriaBuilder,
                                  List<Predicate> predicateList, Join<Neighborhood, City> cityJoin) {
        if (apartmentSearch.getCityName() != null && !apartmentSearch.getCityName().trim().isEmpty()) {
            Predicate cityNamePredicate = criteriaBuilder.like(
                    cityJoin.get("name"), apartmentSearch.getCityName() + "%");
            predicateList.add(cityNamePredicate);
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