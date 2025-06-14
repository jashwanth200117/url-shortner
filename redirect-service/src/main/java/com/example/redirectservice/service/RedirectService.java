package com.example.redirectservice.service;

import com.example.redirectservice.client.UrlShortenerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RedirectService {

    @Autowired
    private UrlShortenerClient urlShortenerClient;

    /**
     * Retrieve the original URL for the given short code
     */
    public Mono<String> getOriginalUrl(String shortCode) {
        return urlShortenerClient.getOriginalUrl(shortCode);
    }
}
