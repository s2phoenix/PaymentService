package com.example.POCBankService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    private String userId;
    private String pin;
    private String sourceAccount;
    private String destinationAccount;
    private double amount;
}
