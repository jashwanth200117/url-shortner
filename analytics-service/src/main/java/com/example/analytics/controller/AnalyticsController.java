package com.example.analytics.controller;

import com.example.analytics.model.ClickEvent;
import com.example.analytics.repository.ClickEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final ClickEventRepository clickEventRepository;

    /**
     * Get analytics for a specific short code.
     */
    @GetMapping("/{shortCode}")
    public List<ClickEvent> getAnalytics(@PathVariable String shortCode) {
        return clickEventRepository.findByShortCode(shortCode);
    }
}
