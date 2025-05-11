package com.example.POCBankService.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRegisterRequestModel {
    @NotBlank
    private String userId;

    @NotBlank
    private String nameTH;

    @NotBlank
    private String nameEN;
}
