package com.example.PaymentService.repository;

import com.example.PaymentService.entity.TransactionHolding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionHoldingRepository extends JpaRepository<TransactionHolding, Long> {
    Optional<TransactionHolding> findByTransactionId(String transactionId);
}
