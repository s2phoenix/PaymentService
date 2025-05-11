package com.example.POCBankService.repository;

import com.example.POCBankService.entity.UserInfo;
import com.example.POCBankService.model.enumconstant.UserRole;
import com.example.POCBankService.model.enumconstant.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByUserIdAndAccount(String userId, String account);
    Optional<UserInfo> findByAccount(String account);
    List<UserInfo> findByRoleAndStatus(UserRole role, UserStatus status);
    List<UserInfo> findByUserId(String userId);
    Optional<UserInfo> findFirstByUserIdAndAccount(String citizenId, String account);
    Optional<UserInfo> findFirstByAccount(String account);
}

