package com.example.PaymentService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "transaction_holding", schema = "reborn")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionHolding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private String payee;

    @Column(nullable = false)
    private ZonedDateTime timestamp;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Integer retrycount = 0;
}