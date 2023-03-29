package com.example.rentify.specs;

import com.example.rentify.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ApartmentSearchSpecification implements Specification<Apartment> {

    private final Filter filter;
    private final List<Integer> ids;

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
        if (ids != null && !ids.isEmpty()) { //filterByApartmentsIDs
            Predicate apartmentIdPredicate = root.get("id").in(ids);
            predicateList.add(apartmentIdPredicate);
        }
        filter.all(root, criteriaBuilder, predicateList);
        query.distinct(true);
        //We return one predicate by combining all predicates with AND
        // which acts as the WHERE condition for query satisfaction"
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}