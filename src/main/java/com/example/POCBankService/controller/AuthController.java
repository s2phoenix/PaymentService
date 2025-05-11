package com.example.POCBankService.controller;

import com.example.POCBankService.model.request.LoginRequest;
import com.example.POCBankService.model.response.AuthResponse;
import com.example.POCBankService.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthResponse login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            throw new BadCredentialsException("Username or password is missing");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        String accessToken = jwtUtils.generateToken(user.getUsername(), user.getAuthorities().toString());
        String refreshToken = jwtUtils.generateRefreshToken(user.getUsername());

        return new AuthResponse(accessToken, refreshToken);
    }
}
