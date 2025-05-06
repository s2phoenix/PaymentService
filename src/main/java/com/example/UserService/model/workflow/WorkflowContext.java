package com.example.UserService.model.workflow;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class WorkflowContext {
    private Map<String, Object> data = new HashMap<>();
    // Add methods to get/set shared data between steps
    String workflowState = "";
}
