package com.example.POCBankService.repository.specification;

import com.example.POCBankService.entity.UserInfo;
import org.springframework.data.jpa.domain.Specification;

public class UserInfoSpecification {

    public static Specification<UserInfo> hasStatusActive(Boolean status) {
        return (root, query, builder) ->
                status == null ? null : builder.equal(root.get("statusActive"), status);
    }

    public static Specification<UserInfo> hasUserId(String userId) {
        return (root, query, builder) ->
                userId == null ? null : builder.equal(root.get("userId"), userId);
    }

    public static Specification<UserInfo> hasAccount(String account) {
        return (root, query, builder) ->
                account == null ? null : builder.equal(root.get("account"), account);
    }

    public static Specification<UserInfo> hasNameLike(String name) {
        return (root, query, builder) -> {
            if (name == null || name.isEmpty()) {
                return null;
            }
            return builder.or(
                    builder.like(builder.lower(root.get("thaiName")), "%" + name.toLowerCase() + "%"),
                    builder.like(builder.lower(root.get("engName")), "%" + name.toLowerCase() + "%")
            );
        };
    }
}

