package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Event;

public interface EventService {
    Event save(Event event);
    Integer getEventSize();
    Page<Event> getEvents(Integer pageSize, Integer pageNumber);
    Page<Event> getEvents(String title, Pageable pageable);
    Event getEvent(Long id);
}
