package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Event;
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAll();

    Page<Event> findByTitleContaining(String title, Pageable pageRequest);

    Page<Event> findByTitleContainingOrDescriptionContaining(String title, String description, Pageable pageRequest);

    Page<Event> findByTitleContainingAndDescriptionContaining(String title, String description, Pageable pageRequest);

    Page<Event> findByTitleContainingOrDescriptionContainingOrOrganizer_NameContaining(String title, String description, String organizerName, Pageable pageRequest);
}