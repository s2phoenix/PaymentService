package com.example.POCBankService.service;

import com.example.POCBankService.entity.TransactionInfo;
import com.example.POCBankService.entity.UserInfo;
import com.example.POCBankService.model.AccountBalance;
import com.example.POCBankService.model.TransactionItem;
import com.example.POCBankService.model.response.TransactionStatementResponse;
import com.example.POCBankService.repository.TransactionInfoRepository;
import com.example.POCBankService.repository.UserInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserInfoRepository userInfoRepository;
    private final TransactionInfoRepository transactionInfoRepository;

    public List<TransactionStatementResponse> getCustomerStatement(String userId) {
        List<UserInfo> users = userInfoRepository.findByUserId(userId); // citizen ID

        Map<String, List<TransactionItem>> groupedByAccount = new HashMap<>();

        for (UserInfo user : users) {
            List<TransactionInfo> transactions = transactionInfoRepository
                    .findByUserAccount_AccountOrderByTransactionDateDescTransactionTimeDesc(user.getAccount());

            List<TransactionItem> transactionItems = transactions.stream().map(tx -> {
                return new TransactionItem(
                        tx.getTransactionDate().format(DateTimeFormatter.ofPattern("dd/MM/yy")),
                        tx.getTransactionTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                        tx.getCode(),
                        tx.getChannel(),
                        tx.getDebitCredit(),
                        tx.getBalance(),
                        tx.getRemark()
                );
            }).collect(Collectors.toList());

            groupedByAccount.put(user.getAccount(), transactionItems);
        }

        return groupedByAccount.entrySet().stream()
                .map(entry -> new TransactionStatementResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public void deposit(String account, Double amount) {
        UserInfo userAccount = userInfoRepository.findFirstByAccount(account)
                .orElseThrow(() -> new RuntimeException("User not found with citizen ID and account."));

        Optional<TransactionInfo> lastTransaction = transactionInfoRepository
                .findTopByUserAccountOrderByTransactionDateDescTransactionTimeDesc(userAccount);

        double lastBalance = lastTransaction.map(TransactionInfo::getBalance).orElse(0.0);
        double newBalance = lastBalance + amount;

        TransactionInfo transaction = TransactionInfo.builder()
                .userAccount(userAccount)
                .transactionDate(LocalDate.now())
                .transactionTime(LocalTime.now())
                .code("DP")
                .debitCredit(amount >= 0 ? "+" + String.format("%.2f", amount) : String.format("%.2f", amount))
                .channel("WEB")
                .balance(newBalance)
                .remark("Deposit of " + amount)
                .build();

        transactionInfoRepository.save(transaction);
    }

    public List<AccountBalance> getAccountsAndBalance(String userId) {
        return transactionInfoRepository.findAllAccountsWithLatestBalance(userId);
    }

    @Transactional
    public void transfer(String userId, String pin, String sourceAccount, String destinationAccount, double amount) {
        // Step 1: Validate source account and PIN
        UserInfo sourceUser = userInfoRepository.findByUserIdAndAccount(userId, sourceAccount)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));

        if (!sourceUser.getPin().equals(pin)) {
            throw new IllegalArgumentException("PIN not match");
        }

        // Step 2: Check if balance is enough
        Optional<TransactionInfo> lastSourceAccountTransaction = transactionInfoRepository
                .findTopByUserAccountOrderByTransactionDateDescTransactionTimeDesc(sourceUser);
        double newSourceBalance = 0;
        if(lastSourceAccountTransaction.isPresent()){
            if (lastSourceAccountTransaction.get().getBalance() < amount) {
                throw new IllegalArgumentException("Insufficient balance");
            }
        } else {
            throw new IllegalArgumentException("Insufficient balance");
        }

        newSourceBalance = lastSourceAccountTransaction.get().getBalance() - amount;
        // Step 3: Withdraw from source account
        TransactionInfo debitTransaction = new TransactionInfo();
        debitTransaction.setTransactionDate(LocalDate.now());
        debitTransaction.setTransactionTime(LocalTime.now());
        debitTransaction.setCode("TR"); // Transaction code
        debitTransaction.setDebitCredit("-" + amount); // Withdraw
        debitTransaction.setChannel("WEB");
        debitTransaction.setBalance(newSourceBalance);
        debitTransaction.setRemark("Transfer from" + sourceAccount + " To " + destinationAccount);
        debitTransaction.setUserAccount(sourceUser);

        // Step 4: Deposit to destination account
        UserInfo destinationUser = userInfoRepository.findByAccount(destinationAccount)
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found"));
        Optional<TransactionInfo> lastDestinationAccountTransaction = transactionInfoRepository
                .findTopByUserAccountOrderByTransactionDateDescTransactionTimeDesc(destinationUser);

        double newDestinationBalance = 0;
        if(lastDestinationAccountTransaction.isPresent()){
            newDestinationBalance = lastDestinationAccountTransaction.get().getBalance() + amount;
        } else {
            newDestinationBalance = amount;
        }
        TransactionInfo creditTransaction = new TransactionInfo();
        creditTransaction.setTransactionDate(LocalDate.now());
        creditTransaction.setTransactionTime(LocalTime.now());
        creditTransaction.setCode("TR"); // Transaction code
        creditTransaction.setDebitCredit("+" + amount); // Deposit
        creditTransaction.setChannel("WEB");
        creditTransaction.setBalance(newDestinationBalance);
        creditTransaction.setRemark("Received from " + sourceAccount);
        creditTransaction.setUserAccount(destinationUser);

        transactionInfoRepository.save(debitTransaction);
        transactionInfoRepository.save(creditTransaction);
    }
}
