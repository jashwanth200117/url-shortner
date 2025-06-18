
package com.example.urlshortner.controller;

import com.example.urlshortner.dto.ShortenRequest;
import com.example.urlshortner.dto.ShortenResponse;
import com.example.urlshortner.service.UrlShortenerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/shorten")
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService service;

    @PostMapping
    public ResponseEntity<ShortenResponse> createShortUrl(@Valid @RequestBody ShortenRequest request ,
                                                          @RequestHeader("X-User-Name") String username) {
        String originalUrl = request.getOriginalUrl();
        String shortenedUrl = service.shortenUrl(originalUrl , username);

        System.out.println("NAmE" + username);

        ShortenResponse response = new ShortenResponse(
                true,
                "Short URL created successfully",
                shortenedUrl
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<?> getOriginalUrl(@PathVariable String shortCode) {
        String originalUrl = service.getOriginalUrl(shortCode);

        if (originalUrl == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Short URL not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ShortenResponse response = new ShortenResponse(
                true,
                "Original URL retrieved successfully",
                originalUrl
        );

        return ResponseEntity.ok(response);
    }
}
