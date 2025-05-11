package com.example.POCBankService.controller;

import com.example.POCBankService.model.workflow.WorkflowContext;
import com.example.POCBankService.service.workflow.master.WorkflowEngine;
import com.example.POCBankService.service.workflow.master.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workflow")
public class WorkflowController {
    //Please review the class WorkflowRegistry to see how to combine multiple  microservice into a group of service.
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private WorkflowEngine workflowEngine;

    //api for test single workflow service
    @GetMapping("/{startStep}")
    public ResponseEntity<String> runWorkflow(@PathVariable String startStep) {
        WorkflowService service = (WorkflowService) applicationContext.getBean(startStep);
        WorkflowContext context = new WorkflowContext();
        service.execute(context);
        return ResponseEntity.ok("Workflow completed");
    }

    //api for test workflow engine
    @GetMapping("/runServiceEngine/{workflowName}")
    public ResponseEntity<String> run(@PathVariable String workflowName) {
        workflowEngine.runWorkflow(workflowName);
        return ResponseEntity.ok("Workflow completed");
    }
}