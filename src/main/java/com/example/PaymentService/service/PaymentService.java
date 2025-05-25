package com.example.PaymentService.service;

import com.example.PaymentService.entity.TransactionHolding;
import com.example.PaymentService.entity.TransactionInfo;
import com.example.PaymentService.entity.UserInfo;
import com.example.PaymentService.model.request.PaymentRequest;
import com.example.PaymentService.repository.TransactionHoldingRepository;
import com.example.PaymentService.repository.TransactionInfoRepository;
import com.example.PaymentService.repository.UserInfoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@Transactional
public class PaymentService {

    private final TransactionHoldingRepository holdingRepository;
    private final TransactionInfoRepository transactionInfoRepository;
    private final UserInfoRepository userInfoRepository;

    public PaymentService(TransactionHoldingRepository holdingRepository,
                          TransactionInfoRepository transactionInfoRepository, UserInfoRepository userInfoRepository) {
        this.holdingRepository = holdingRepository;
        this.transactionInfoRepository = transactionInfoRepository;
        this.userInfoRepository = userInfoRepository;
    }

    public Mono<Boolean> processTransaction(PaymentRequest request) {
        return Mono.fromCallable(() -> {
            String transactionId = request.getTransactionId();
            double amount = request.getAmount();

            // Blocking DB calls wrapped in Mono
            Optional<UserInfo> existingUser = userInfoRepository.findByAccount("1743732");
            if (existingUser.isEmpty()) {
                return false;
            }

            Optional<TransactionHolding> holdingOpt = holdingRepository.findByTransactionId(transactionId);
            if (holdingOpt.isPresent() && holdingOpt.get().getRetrycount() > 3) {
                return false;
            }

            try {
                Optional<TransactionInfo> lastTransaction = transactionInfoRepository
                        .findTopByUserAccountOrderByTransactionDateDescTransactionTimeDesc(existingUser.get());

                double lastBalance = lastTransaction.map(TransactionInfo::getBalance).orElse(0.0);
                double newBalance = lastBalance + amount;

                TransactionInfo transaction = TransactionInfo.builder()
                        .userAccount(existingUser.get())
                        .transactionDate(LocalDate.now())
                        .transactionTime(LocalTime.now())
                        .code("DP")
                        .debitCredit(amount >= 0
                                ? "+" + String.format("%.2f", amount)
                                : String.format("%.2f", amount))
                        .channel("BATCH")
                        .balance(newBalance)
                        .remark("Deposit of " + amount)
                        .build();

                transactionInfoRepository.save(transaction);
                return true;

            } catch (Exception e) {
                holdingOpt.ifPresent(holding -> {
                    holding.setRetrycount(holding.getRetrycount() + 1);
                    holdingRepository.save(holding);
                });
                return false;
            }
        }).subscribeOn(Schedulers.boundedElastic()); // Run on separate thread due to blocking DB access
    }
}