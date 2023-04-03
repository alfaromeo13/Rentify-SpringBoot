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
            root.fetch("address", JoinType.LEFT);
            root.fetch("user", JoinType.LEFT);
            root.fetch("images", JoinType.LEFT);
            root.fetch("apartmentAttributes", JoinType.LEFT);
            // we know that at the end we want to return DTO class instead of Entity class
            // When mapping entity class fields to dto class fields our mapper calls 'get()'
            // method in background.Get() method will if that field is null make additional
            // sql query to fetch that data .That is why we need to join fetch so that we don't
            // make additional calls for those values.Instead we fetch them with response
        }

//        if (ids != null && !ids.isEmpty())
        predicateList.add(root.get("id").in(ids)); //filter by apartment id's pise se ovako? i treba li mi if

        filter.all(root, criteriaBuilder, predicateList);
        query.distinct(true);
        //We return one predicate by combining all predicates with AND
        // which acts as the WHERE condition for query satisfaction"
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}