package com.example.analytics.repository;

import com.example.analytics.model.ClickEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClickEventRepository extends MongoRepository<ClickEvent, String> {

    List<ClickEvent> findByShortCode(String shortCode);

}
