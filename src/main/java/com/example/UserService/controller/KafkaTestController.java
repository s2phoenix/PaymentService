package com.example.UserService.controller;

import com.example.UserService.service.KafkaExampleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kafka")
public class KafkaTestController {

    private final KafkaExampleService kafkaExampleService;

    public KafkaTestController(KafkaExampleService kafkaExampleService) {
        this.kafkaExampleService = kafkaExampleService;
    }

    @GetMapping("/messages")
    public List<String> getMessages() {
        return kafkaExampleService.getConsumedMessages();
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendKafkaMessage(@RequestParam String message) {
        kafkaExampleService.sendMessage(message);
        return ResponseEntity.ok("Message sent to Kafka: " + message);
    }
}