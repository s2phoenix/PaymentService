package com.example.POCBankService.service.workflow.master;

import com.example.POCBankService.component.WorkflowRegistry;
import com.example.POCBankService.model.workflow.WorkflowContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkflowEngine {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private WorkflowRegistry workflowRegistry;

    public void runWorkflow(String workflowName) {
        List<String> steps = workflowRegistry.getSteps(workflowName);

        WorkflowContext context = new WorkflowContext();

        for (String step : steps) {
            WorkflowService service = (WorkflowService) applicationContext.getBean(step);
            service.execute(context);
        }
    }
}
