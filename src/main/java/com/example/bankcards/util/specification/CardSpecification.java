package com.example.bankcards.util.specification;

import com.example.bankcards.dto.Request.RequestFilterCard;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CardSpecification {
    public static Specification<Card> getSpecification(RequestFilterCard requestFilterCard) {
        return ((root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (requestFilterCard.status() != null) {
                predicates.add(cb.equal(root.get("status"), requestFilterCard.status()));
            }

            if (requestFilterCard.number() != null) {
                predicates.add(cb.like(root.get("number"), "%" + requestFilterCard.number() + "%"));
            }

            if (requestFilterCard.username() != null) {
                Join<Card, User> userJoin = root.join("user");
                predicates.add(cb.like(userJoin.get("username"), "%" + requestFilterCard.username() + "%"));
            }

            BigDecimal lower = requestFilterCard.lowerLimitBalance();
            BigDecimal upper = requestFilterCard.upperLimitBalance();

            if (lower != null && upper != null) {
                predicates.add(cb.between(root.get("balance"), lower, upper));
            } else if (lower != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("balance"), lower));
            } else if (upper != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("balance"), upper));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}
