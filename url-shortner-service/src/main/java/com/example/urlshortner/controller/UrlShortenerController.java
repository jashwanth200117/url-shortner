
package com.example.urlshortner.controller;

import com.example.urlshortner.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/shorten")
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService service;

    @PostMapping
    public String createShortUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("originalUrl");
        return service.shortenUrl(originalUrl);
    }

//    @PostMapping
//    public String createShortUrl(@RequestBody String request) {
//        String originalUrl = request;
//        return service.shortenUrl(request);
//    }

    @GetMapping("/{shortCode}")
    public String getOriginalUrl(@PathVariable String shortCode) {
        return service.getOriginalUrl(shortCode);
    }
}
