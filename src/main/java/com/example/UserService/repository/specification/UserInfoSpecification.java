package com.example.UserService.repository.specification;

import com.example.UserService.entity.UserInfo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class UserInfoSpecification {

    public static Specification<UserInfo> hasFirstName(String firstName) {
        return (Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (firstName != null) {
                return criteriaBuilder.equal(root.get("firstName"), firstName);
            }
            return null;
        };
    }

    public static Specification<UserInfo> hasLastName(String lastName) {
        return (Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (lastName != null) {
                return criteriaBuilder.equal(root.get("lastName"), lastName);
            }
            return null;
        };
    }

    public static Specification<UserInfo> isActive(Boolean isActive) {
        return (Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (isActive != null) {
                return criteriaBuilder.equal(root.get("statusActive"), isActive);
            }
            return null;
        };
    }

    public static Specification<UserInfo> isMarried(Boolean marriedStatus) {
        return (Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (marriedStatus != null) {
                return criteriaBuilder.equal(root.get("marriedStatus"), marriedStatus);
            }
            return null;
        };
    }
}

