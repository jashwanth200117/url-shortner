package com.example.redirectservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UrlShortenerClient {

    private final WebClient webClient;

    public UrlShortenerClient(@Value("${url-shortener.base-url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<String> getOriginalUrl(String shortCode) {
        return webClient.get()
                .uri("/shorten/{shortCode}", shortCode)
                .retrieve()
                .bodyToMono(String.class);
    }
}
