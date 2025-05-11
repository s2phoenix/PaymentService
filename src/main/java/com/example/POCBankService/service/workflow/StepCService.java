package com.example.POCBankService.service.workflow;

import com.example.POCBankService.model.workflow.WorkflowContext;
import com.example.POCBankService.service.workflow.master.AbstractWorkflowService;
import org.springframework.stereotype.Service;

@Service("stepC")
public class StepCService extends AbstractWorkflowService {

    @Override
    protected void verify(WorkflowContext context) {
        System.out.println("Step C: Verifying");
    }

    @Override
    protected void process(WorkflowContext context) {
        System.out.println("Step C: Processing");
    }

    @Override
    protected void finalizeStep(WorkflowContext context) {
        System.out.println("Step C: Finalizing");
    }
}