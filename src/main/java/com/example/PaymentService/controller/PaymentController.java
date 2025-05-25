package com.example.PaymentService.controller;

import com.example.PaymentService.model.request.PaymentRequest;
import com.example.PaymentService.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/submit")
    public Mono<ResponseEntity<String>> submitPayment(@RequestBody PaymentRequest request) {
        return paymentService.processTransaction(request)
                .map(success -> {
                    if (success) {
                        return ResponseEntity.ok("Transaction submitted successfully.");
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transaction failed.");
                    }
                });
    }
}
