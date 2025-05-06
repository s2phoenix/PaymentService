package com.example.UserService.service.workflow.master;

import com.example.UserService.model.workflow.WorkflowContext;

public interface WorkflowService {
    void execute(WorkflowContext context);
}