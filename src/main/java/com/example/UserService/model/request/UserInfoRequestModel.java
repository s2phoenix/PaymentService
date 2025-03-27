package com.example.UserService.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoRequestModel {
    private String firstName;
    private String lastName;
    private Boolean marriedStatus;
}
