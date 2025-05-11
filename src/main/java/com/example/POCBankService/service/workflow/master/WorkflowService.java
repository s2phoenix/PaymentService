package com.example.POCBankService.service.workflow.master;

import com.example.POCBankService.model.workflow.WorkflowContext;

public interface WorkflowService {
    void execute(WorkflowContext context);
}