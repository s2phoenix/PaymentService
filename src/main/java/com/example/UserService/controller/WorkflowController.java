package com.example.UserService.controller;

import com.example.UserService.model.workflow.WorkflowContext;
import com.example.UserService.service.workflow.master.WorkflowEngine;
import com.example.UserService.service.workflow.master.WorkflowService;
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

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private WorkflowEngine workflowEngine;

    @GetMapping("/{startStep}")
    public ResponseEntity<String> runWorkflow(@PathVariable String startStep) {
        WorkflowService service = (WorkflowService) applicationContext.getBean(startStep);
        WorkflowContext context = new WorkflowContext();
        service.execute(context);
        return ResponseEntity.ok("Workflow completed");
    }

    @GetMapping("/runServiceEngine/{workflowName}")
    public ResponseEntity<String> run(@PathVariable String workflowName) {
        workflowEngine.runWorkflow(workflowName);
        return ResponseEntity.ok("Workflow completed");
    }
}