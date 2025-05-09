package com.example.UserService.controller;

import com.example.UserService.entity.UserInfo;
import com.example.UserService.model.dto.UserInfoDTO;
import com.example.UserService.model.request.UserInfoRequestModel;
import com.example.UserService.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/user") // Base URL for the controller
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/findUserInfo")
    public Mono<List<UserInfo>> findUserInfo(@RequestBody UserInfoRequestModel userInfoRequestModel) {
        return userInfoService.findUsers(userInfoRequestModel.getFirstName(),
                userInfoRequestModel.getLastName(),
                true,
                userInfoRequestModel.getMarriedStatus());
    }

    @PostMapping("/create")
    public UserInfo createUserInfo(@RequestBody UserInfoDTO userInfoDTO) {
        return userInfoService.createUserInfo(userInfoDTO);
    }

    @PutMapping("/update/{id}")
    public UserInfo updateUserInfo(@PathVariable Long id, @RequestBody UserInfoDTO userInfoDTO) {
        return userInfoService.updateUserInfo(id, userInfoDTO);
    }

    @DeleteMapping("/delete/{id}")
    public UserInfo softDeleteUserInfo(@PathVariable Long id) {
        return userInfoService.softDeleteUserInfo(id);
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/checkRole")
    public String checkRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        String role = authentication.getAuthorities().toString();  // Role from JWT

        return "Username: " + username + ", Role: " + role;
    }
}
