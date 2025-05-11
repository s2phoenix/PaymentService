package com.example.POCBankService.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApprovalRequest {
    private String account;
    private String action;
}
