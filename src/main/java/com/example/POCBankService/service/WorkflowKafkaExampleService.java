package com.example.POCBankService.service;

import lombok.Getter;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkflowKafkaExampleService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Getter
    private final List<String> consumedMessages = new ArrayList<>();

    public WorkflowKafkaExampleService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

//    @KafkaListener(topics = "workflow-topic", groupId = "workflow-group")
    public void consume(String message) {
        consumedMessages.add(message);
        System.out.println("Consumed message: " + message);
    }

    public void sendMessage(String message) {
        kafkaTemplate.send("workflow-topic", message);
        System.out.println("Message sent: " + message);
    }
}
