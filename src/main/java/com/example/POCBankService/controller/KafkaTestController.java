package com.example.POCBankService.controller;

import com.example.POCBankService.service.KafkaExampleService;
import com.example.POCBankService.service.WorkflowKafkaExampleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kafka")
public class KafkaTestController {

    private final KafkaExampleService kafkaExampleService;
    private final WorkflowKafkaExampleService workflowKafkaExampleService;

    public KafkaTestController(KafkaExampleService kafkaExampleService, WorkflowKafkaExampleService workflowKafkaExampleService) {
        this.kafkaExampleService = kafkaExampleService;
        this.workflowKafkaExampleService = workflowKafkaExampleService;
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

    //this api use for trigger workflow consume. after producer get the data. it will do the workflow service .
    @PostMapping("/workflow/send")
    public ResponseEntity<String> sendWorkflowKafkaMessage(@RequestParam String message) {
        workflowKafkaExampleService.sendMessage(message);
        return ResponseEntity.ok("Message sent to Kafka workflow-topic: " + message);
    }
}