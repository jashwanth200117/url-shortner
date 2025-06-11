package com.example.redirectservice.controller;

import com.example.redirectservice.model.ClickEvent;
import com.example.redirectservice.service.RedirectService;
import com.example.redirectservice.service.ClickEventPublisher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/redirect")
public class RedirectController {

    @Autowired
    private RedirectService redirectService;
    private final ClickEventPublisher clickEventPublisher;

    public RedirectController(RedirectService redirectService, ClickEventPublisher clickEventPublisher) {
        this.redirectService = redirectService;
        this.clickEventPublisher = clickEventPublisher;
    }

    /**
     * Handle redirection from short URL to the original URL
     */
//    @GetMapping("{shortCode}")
//    public Mono<ResponseEntity<Void>> redirect(@PathVariable String shortCode) {
//        return redirectService.getOriginalUrl(shortCode)
//                .map(originalUrl -> ResponseEntity.status(HttpStatus.FOUND)
//                        .header("Location", originalUrl)
//                        .build());
//    }

    @GetMapping("/{shortCode}")
    public Mono<ResponseEntity<Void>> redirect(@PathVariable String shortCode, HttpServletRequest request) {
        return redirectService.getOriginalUrl(shortCode)
                .map(originalUrl -> {
                    // 🔥 Create ClickEvent
                    ClickEvent clickEvent = new ClickEvent(
                            shortCode,
                            originalUrl,
                            request.getHeader("User-Agent"),
                            request.getRemoteAddr(),
                            System.currentTimeMillis()
                    );

                    // 🔥 Publish ClickEvent to Kafka
                    clickEventPublisher.publishClickEvent(clickEvent);

                    // 🔥 Return redirect response
                    return ResponseEntity.status(HttpStatus.FOUND)
                            .header("Location", originalUrl)
                            .build();
                });
    }
}
