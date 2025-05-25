package com.example.PaymentService.model.request;

import lombok.Data;

@Data
public class PaymentRequest {
    private String transactionId;
    private double amount;
    private String currency;
    private String payee;
}
