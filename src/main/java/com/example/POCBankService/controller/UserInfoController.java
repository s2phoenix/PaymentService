package com.example.POCBankService.controller;

import com.example.POCBankService.entity.UserInfo;
import com.example.POCBankService.model.request.ApprovalRequest;
import com.example.POCBankService.model.request.CustomerRegisterRequestModel;
import com.example.POCBankService.model.request.RegisterRequestModel;
import com.example.POCBankService.model.response.UserDetailResponse;
import com.example.POCBankService.service.UserInfoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserInfoController {

    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping(value = "/preregister", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> preRegister(@Valid @RequestBody RegisterRequestModel request) {
        String result = userInfoService.preRegister(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/customerregister", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> customerRegister(@Valid @RequestBody CustomerRegisterRequestModel request) {
        String result = userInfoService.customerRegister(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/preregister/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserInfo>> listPendingPersons() {
        return ResponseEntity.ok(userInfoService.getPendingPersons());
    }

    @PostMapping(value = "/preregister/approval", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> approveOrRejectUser(@RequestBody ApprovalRequest request) {
        return ResponseEntity.ok(userInfoService.updateUserApprovalStatus(request));
    }

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetailResponse> getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = authentication.getName();
        String role = authentication.getAuthorities().toString().replaceAll("[\\[\\]]", "");

        UserDetailResponse response = new UserDetailResponse(username, role);
        return ResponseEntity.ok(response);
    }
}
