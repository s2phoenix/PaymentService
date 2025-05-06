package com.example.UserService.service.workflow;

import com.example.UserService.model.workflow.WorkflowContext;
import com.example.UserService.service.workflow.master.AbstractWorkflowService;
import org.springframework.stereotype.Service;

@Service("stepB")
public class StepBService extends AbstractWorkflowService {

    @Override
    protected void verify(WorkflowContext context) {
        System.out.println("Step B: Verifying");
        System.out.println("PREVIOUS STEP:" + context.getWorkflowState());
    }

    @Override
    protected void process(WorkflowContext context) {
        System.out.println("Step B: Processing");
    }

    @Override
    protected void finalizeStep(WorkflowContext context) {
        System.out.println("Step B: Finalizing");
    }
}