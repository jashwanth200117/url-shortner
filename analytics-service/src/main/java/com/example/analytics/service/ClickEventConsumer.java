package com.example.analytics.service;

import com.example.analytics.model.ClickEvent;
import com.example.analytics.repository.ClickEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClickEventConsumer {

    private final ClickEventRepository clickEventRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Listen to the 'click-events' topic and save events to MongoDB.
     */
    @KafkaListener(
            topics = "${spring.kafka.topic.click-events}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(String message) {
        try {
            ClickEvent clickEvent = objectMapper.readValue(message, ClickEvent.class);
            clickEventRepository.save(clickEvent);
            System.out.println("Click event saved: " + clickEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
