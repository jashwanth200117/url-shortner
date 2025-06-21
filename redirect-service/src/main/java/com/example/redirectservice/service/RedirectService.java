package com.example.redirectservice.service;

import com.example.redirectservice.client.UrlShortnerClient;
import com.example.redirectservice.exception.UrlNotFoundException;
import com.example.redirectservice.model.ShortenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RedirectService {

    @Autowired
    private UrlShortnerClient urlShortenerClient;

    /**
     * Retrieve the original URL for the given short code
     */
    public String getOriginalUrl(String shortCode) {
        ShortenResponse response = urlShortenerClient.getOriginalUrl(shortCode);

        if (response == null || !response.isSuccess()) {
            throw new UrlNotFoundException("Short URL not found: " + shortCode);
        }

        return response.getShortUrl();
    }
}
