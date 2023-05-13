package com.example.rentify.specs;

import com.example.rentify.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Component
@RequiredArgsConstructor
public class ApartmentSearchSpecification implements Specification<Apartment> {

    private List<Integer> ids;
    private final Filter filter;

    @Override
    public Predicate toPredicate(Root<Apartment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();
        if (!query.getResultType().equals(Long.class)) {
            root.fetch("address", JoinType.LEFT)
            .fetch("neighborhood", JoinType.LEFT);
            root.fetch("user", JoinType.LEFT);
            root.fetch("period", JoinType.LEFT);
            root.fetch("images", JoinType.LEFT);
            root.fetch("apartmentAttributes", JoinType.LEFT)
            .fetch("attribute", JoinType.LEFT);
            // we know that at the end we want to return DTO class instead of Entity class
            // When mapping entity class fields to dto class fields our mapper calls 'get()'
            // method in background.Get() method, will for null field make additional sql
            // query to fetch that data .That is why we need to 'join fetch' so that we don't
            // make additional calls for those values.Instead we fetch them all in same response
        }
        predicateList.add(root.get("id").in(ids));
        filter.all(root, criteriaBuilder, predicateList);
        query.distinct(true);
        //We return one predicate by combining all predicates with AND(we make our WHERE condition for later query)
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}