package com.example.rentify.specs;

import com.example.rentify.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApartmentIdSpecification implements Specification<Apartment> {

    private final Filter filter;

    @Override //this one retrieves apartment ids
    public Predicate toPredicate(Root<Apartment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();
        filter.all(root, criteriaBuilder, predicateList);
        query.distinct(true);
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}