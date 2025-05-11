package com.example.POCBankService.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestModel {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String citizenId;

    @NotBlank
    private String nameTH;

    @NotBlank
    private String nameEN;

    @NotBlank
    private String pin;
}
