package com.example.redirectservice.client;

import com.example.redirectservice.exception.UrlNotFoundException;
import com.example.redirectservice.model.ShortenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

    public Mono<ShortenResponse> getOriginalUrl(String shortCode) {
        return webClient.get()
                .uri("/shorten/{shortCode}", shortCode)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), clientResponse ->
                        Mono.error(new UrlNotFoundException("Short URL not found: " + shortCode))
                )
                .onStatus(status -> status.is5xxServerError(), clientResponse ->
                        Mono.error(new RuntimeException("Server error occurred while retrieving URL"))
                )
                .bodyToMono(ShortenResponse.class)
                .flatMap(response -> {
                    if (response.isSuccess()) {
                        System.out.println("✅ success");
                        return Mono.just(response);
                    } else {
                        System.out.println("❌ failure");
                        return Mono.error(new UrlNotFoundException("Short URL not found: " + shortCode));
                    }
                });
    }


}
