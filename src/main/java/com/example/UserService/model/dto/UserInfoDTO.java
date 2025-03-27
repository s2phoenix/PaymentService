package com.example.UserService.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDTO {

    private String title;
    private String firstName;
    private String lastName;
    private String middleName;
    private Integer age;
    private String birthdate;
    private Boolean disableStatus;
    private Boolean marriedStatus;
}

