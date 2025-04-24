package com.example.UserService.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

@EmbeddedKafka(partitions = 1, topics = { "my-topic" })
@SpringBootTest
class KafkaIntegrationTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    void sendMessage() {
        kafkaTemplate.send("my-topic", "Integration test message");
        // You can verify it was consumed or logged
    }
}
