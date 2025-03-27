package com.example.UserService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api") // Base URL for the controller
public class HelloController {

    @GetMapping("/hello") // Maps GET request for "/api/hello"
    public Mono<String> hello() {
        return Mono.just("Hello, WebFlux!");
    }
}