package com.example.POCBankService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "transaction_info", schema = "reborn")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "transaction_time")
    private LocalTime transactionTime;

    @Column(name = "code", length = 2)
    private String code;

    @Column(name = "debit_credit", length = 20)
    private String debitCredit;

    @Column(name = "channel", length = 50)
    private String channel;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "remark", length = 255)
    private String remark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account", referencedColumnName = "account")
    private UserInfo userAccount;
}