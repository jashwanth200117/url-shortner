package com.example.auth.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class test {

    @GetMapping("/hello")
    public Mono<String> hello() {
        return Mono.just("Hello, authenticated user!");
    }
}
