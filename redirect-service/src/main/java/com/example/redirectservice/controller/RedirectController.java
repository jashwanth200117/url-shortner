package com.example.redirectservice.controller;

import com.example.redirectservice.exception.UrlNotFoundException;
import com.example.redirectservice.model.ClickEvent;
import com.example.redirectservice.model.ShortenResponse;
import com.example.redirectservice.service.RedirectService;
import com.example.redirectservice.service.ClickEventPublisher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/redirect")
public class RedirectController {

    private static final Logger logger = LoggerFactory.getLogger(RedirectController.class);

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
        logger.info("Redirect request received for shortCode: {}", shortCode);

        return redirectService.getOriginalUrl(shortCode)
                .map(originalUrl -> {
                    // 🔥 Create and publish click event
                    ClickEvent clickEvent = new ClickEvent(
                            shortCode,
                            originalUrl,
                            request.getHeader("User-Agent"),
                            request.getRemoteAddr(),
                            System.currentTimeMillis()
                    );
                    clickEventPublisher.publishClickEvent(clickEvent);

                    logger.info("Redirecting to: {}", originalUrl);

                    return ResponseEntity.status(HttpStatus.FOUND)
                            .header("Location", originalUrl)
                            .build();
                });
    }

}
