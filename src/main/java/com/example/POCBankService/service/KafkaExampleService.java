package com.example.POCBankService.service;

import lombok.Getter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaExampleService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Getter
    private final List<String> consumedMessages = new ArrayList<>();

    public KafkaExampleService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "my-topic", groupId = "user-prefill")
    public void consume(String message) {
        consumedMessages.add(message);
        System.out.println("Consumed message: " + message);
    }

    public void sendMessage(String message) {
        kafkaTemplate.send("my-topic", message);
        System.out.println("Message sent: " + message);
    }
}
