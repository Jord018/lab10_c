package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dao.EventDao;
import com.example.demo.dao.OrganizerDao;
import com.example.demo.entity.Event;
import com.example.demo.entity.Organizer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{

    final EventDao eventDao;
    final OrganizerDao organizerDao;

    @Override
    public Integer getEventSize() {
        return eventDao.getEventSize();
    }

    @Override
    public Page<Event> getEvents(Integer pageSize, Integer page) {
        return eventDao.getEvents(pageSize, page);
    }

    @Override
    public Event getEvent(Long id) {
        return eventDao.getEvent(id);
    }

    @Override
    @Transactional
    public Event save(Event event) {
        if (event.getOrganizer() == null || event.getOrganizer().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The organizer id is required");
        }
        if (event.getParticipants() == null || event.getParticipants().size() < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Each event must have at least 3 participants");
        }
        Organizer organizer = organizerDao.findById(event.getOrganizer().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Organizer not found"));
        event.setOrganizer(organizer);
        organizer.getOwnEvents().add(event);
        return eventDao.save(event);
    }
}
