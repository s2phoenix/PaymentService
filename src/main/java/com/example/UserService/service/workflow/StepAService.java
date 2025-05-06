package com.example.UserService.service.workflow;

import com.example.UserService.model.workflow.WorkflowContext;
import com.example.UserService.service.workflow.master.AbstractWorkflowService;
import org.springframework.stereotype.Service;

@Service("stepA")
public class StepAService extends AbstractWorkflowService {

    @Override
    protected void verify(WorkflowContext context) {
        System.out.println("Step A: Verifying");
    }

    @Override
    protected void process(WorkflowContext context) {
        System.out.println("Step A: Processing");
    }

    @Override
    protected void finalizeStep(WorkflowContext context) {
        System.out.println("Step A: Finalizing");
        context.setWorkflowState("FINISH SERVICE A");
    }
}