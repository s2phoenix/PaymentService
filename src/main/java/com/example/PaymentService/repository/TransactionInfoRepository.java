package com.example.PaymentService.repository;

import com.example.PaymentService.entity.TransactionInfo;
import com.example.PaymentService.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionInfoRepository extends JpaRepository<TransactionInfo, Long> {
    Optional<TransactionInfo> findTopByUserAccountOrderByTransactionDateDescTransactionTimeDesc(UserInfo user);
}
