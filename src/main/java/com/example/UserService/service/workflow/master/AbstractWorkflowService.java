package com.example.UserService.service.workflow.master;

import com.example.UserService.model.workflow.WorkflowContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public abstract class AbstractWorkflowService implements WorkflowService {

    @Autowired
    private ApplicationContext applicationContext;

    protected String nextServiceName;

    @Override
    public void execute(WorkflowContext context) {
        verify(context);
        process(context);
        finalizeStep(context);

        if (nextServiceName != null) {
            WorkflowService nextService = (WorkflowService) applicationContext.getBean(nextServiceName);
            nextService.execute(context);
        }
    }

    protected abstract void verify(WorkflowContext context);
    protected abstract void process(WorkflowContext context);
    protected abstract void finalizeStep(WorkflowContext context);
}
