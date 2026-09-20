package com.example.demo.service;

import org.springframework.data.domain.Page;

import com.example.demo.entity.Event;

public interface EventService {
    Event save(Event event);
    Integer getEventSize();
    Page<Event> getEvents(Integer pageSize, Integer pageNumber);
    Event getEvent(Long id);
}
