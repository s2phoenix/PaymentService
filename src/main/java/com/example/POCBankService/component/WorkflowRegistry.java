package com.example.POCBankService.component;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WorkflowRegistry {

    private final Map<String, List<String>> workflowMap = new HashMap<>();

    @PostConstruct
    public void init() {
        // Static example (could be loaded from YAML, DB, etc.)
        workflowMap.put("defaultWorkflow", List.of("stepA", "stepB"));
        workflowMap.put("customWorkflow", List.of("stepB"));
        workflowMap.put("registerProductC", List.of("stepC"));
    }

    public List<String> getSteps(String workflowName) {
        return workflowMap.getOrDefault(workflowName, List.of());
    }
}