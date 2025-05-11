package com.example.POCBankService.model.response;

import com.example.POCBankService.model.TransactionItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionStatementResponse {
    private String account;
    private List<TransactionItem> transactions;
}
