package com.example.UserService.service.workflow.consumerservice;

import com.example.UserService.service.workflow.master.WorkflowEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductRegistrationService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private WorkflowEngine workflowEngine;

    @KafkaListener(topics = "workflow-topic", groupId = "workflow-group")
    public void listen(String workflowName) {
        if ("registerProductC".equals(workflowName)) {
            System.out.println("registerProductC: Processing registerProductC");
            workflowEngine.runWorkflow(workflowName);
        }
    }
}