package com.example.POCBankService.controller;

import com.example.POCBankService.model.AccountBalance;
import com.example.POCBankService.model.TransferRequest;
import com.example.POCBankService.model.response.TransactionStatementResponse;
import com.example.POCBankService.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@CrossOrigin
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping(value = "/statement", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransactionStatementResponse>> getStatement(
            @RequestParam String userId) {
        return ResponseEntity.ok(transactionService.getCustomerStatement(userId));
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(
            @RequestParam String account,
            @RequestParam Double amount) {

        transactionService.deposit(account, amount);
        return ResponseEntity.ok("Deposit successful.");
    }

    @GetMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountBalance>> getAccounts(@RequestParam String userId) {
        return ResponseEntity.ok(transactionService.getAccountsAndBalance(userId));
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {
        transactionService.transfer(
                request.getUserId(),
                request.getPin(),
                request.getSourceAccount(),
                request.getDestinationAccount(),
                request.getAmount()
        );
        return ResponseEntity.ok("Transfer successful");
    }
}
