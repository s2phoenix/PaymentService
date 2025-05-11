package com.example.POCBankService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionItem {
    private String date;         // formatted: dd/MM/yy
    private String time;         // formatted: HH:mm
    private String code;
    private String channel;
    private String debitCredit;
    private Double balance;
    private String remark;
}